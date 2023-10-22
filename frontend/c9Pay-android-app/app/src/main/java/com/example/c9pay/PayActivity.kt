package com.example.c9pay

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.c9pay.module.createQRCode
import com.example.c9pay.module.handleErrorCode
import com.example.c9pay.retrofit.PayResponse
import com.example.c9pay.retrofit.RetrofitInterface
import org.json.JSONObject
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Timer
import java.util.TimerTask

class PayActivity: AppCompatActivity() {
    /* PayActivity <-> MainActivity */
    companion object {
        var payActivity: PayActivity? = null
    }

    // 전역 변수
    val timer = Timer()
    var authorization: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay)

        /* PayActivity <-> MainActivity */
        payActivity = this
        MainActivity.mainActivity?.finish()

        // get Token from Intent
        authorization = intent.getStringExtra("authorization")

        // View 변수 선언 및 연결
        val imgQR: ImageView = findViewById(R.id.imgQR)
        val txtTimer: TextView = findViewById(R.id.txtTimer)

        // 서버 통신
        val retrofitObj = RetrofitInterface.create()
        // API 호출
        retrofitObj.getQR(authorization).enqueue(object: Callback<PayResponse> {
            override fun onResponse(call: Call<PayResponse>, response: Response<PayResponse>) {

                //// 통신 및 호출 성공 시
                if(response.isSuccessful) {
                    // Body 받아오기
                    val responseBody = response.body()!!

                    /// QR 코드
                    // QR 문자열 파싱
                    Log.v("QR", responseBody.toString())
                    val QRstring_original = responseBody.toString()
                    val QRstring_split = QRstring_original.split("(", ")")
                    var QRstr = QRstring_split[1]
                    QRstr = "{" + QRstr
                    QRstr = QRstr.replace("content=", "\"content\": \"")
                    QRstr = QRstr.replace(", expiredAt=", "\", \"expiredAt\": ")
                    QRstr = QRstr.replace(", sign=", ", \"sign\": \"")
                    QRstr = QRstr + "\"}"

                    // Log.v("QR", QRstr)
                    // QR 코드 출력
                    createQRCode(QRstr, imgQR)

                    /// QR 유효시간 타이머
                    // remainTime = 남은 시간
                    var remainTime: Long = (responseBody.expiredAt - System.currentTimeMillis()) / 1000
                    val timerTask = object: TimerTask() {
                        override fun run() {
                            val handler = Handler(Looper.getMainLooper())
                            handler.postDelayed({
                                remainTime--
                                // 시간 초과 시,
                                if (remainTime <= 0) {
                                    timer.cancel()
                                    Toast.makeText(this@PayActivity, "결제를 취소합니다.", Toast.LENGTH_LONG)
                                        .show()
                                    // MainActivity 전환
                                    val toMainIntent = Intent(this@PayActivity, MainActivity::class.java)
                                    toMainIntent.putExtra("authorization", authorization)
                                    startActivity(toMainIntent)
                                }
                                txtTimer.setText(remainTime.toString() + " 초")
                            }, 0)
                        }
                    }
                    timer.schedule(timerTask, 0, 1000)
                }

                //// 통신 성공, 호출 실패 시
                else {
                    val responseErrorBody = JSONTokener(response.errorBody()?.string()!!).nextValue() as JSONObject
                    val errorCode = responseErrorBody.getString("errorCode")
                    Toast.makeText(this@PayActivity, handleErrorCode(errorCode), Toast.LENGTH_SHORT)
                        .show()

                    // MainActivity 전환
                    val toMainIntent = Intent(this@PayActivity, MainActivity::class.java)
                    toMainIntent.putExtra("authorization", authorization)
                    startActivity(toMainIntent)
                }
            }

            //// 통신 실패 시
            override fun onFailure(call: Call<PayResponse>, t: Throwable) {
                Toast.makeText(this@PayActivity, "서버와 연결할 수 없습니다.", Toast.LENGTH_SHORT)
                    .show()

                // MainActivity 전환
                val toMainIntent = Intent(this@PayActivity, MainActivity::class.java)
                toMainIntent.putExtra("authorization", authorization)
                startActivity(toMainIntent)
            }
        })
    }

    // back 버튼 클릭 시, MainActivity 전환
    override fun onBackPressed() {
        timer.cancel()
        Toast.makeText(this@PayActivity, "결제를 취소합니다.",
            Toast.LENGTH_LONG).show()

        // MainActivity 전환
        val toMainIntent = Intent(this@PayActivity, MainActivity::class.java)
        toMainIntent.putExtra("authorization", authorization)
        startActivity(toMainIntent)
    }
}