package com.example.c9pay

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.c9pay.module.handleErrorCode
import com.example.c9pay.module.isInteger
import com.example.c9pay.retrofit.LoadRequest
import com.example.c9pay.retrofit.RetrofitInterface
import org.json.JSONObject
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoadActivity: AppCompatActivity() {
    /* LoadActivity <-> MainActivity */
    companion object {
        var loadActivity: LoadActivity? = null
    }

    // 전역 변수
    var authorization: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load)

        /* LoadActivity <-> MainActivity */
        loadActivity = this
        MainActivity.mainActivity?.finish()

        // get Token from Intent
        authorization = intent.getStringExtra("authorization")

        // View 변수 선언 및 연결
        val edtCharge: EditText = findViewById(R.id.edtCharge)
        val btnCharge: Button = findViewById(R.id.btnCharge)

        // 충전 버튼 클릭 이벤트
        btnCharge.setOnClickListener {
            // 금액이 입력되지 않았다면,
            // 양수 및,
            // 정수 금액이 입력되지 않았다면,
            if (edtCharge.getText().toString().isEmpty() ||
                edtCharge.getText().toString().toInt() <= 0 ||
                !isInteger(edtCharge.getText().toString())
            ) {
                Toast.makeText(
                    this@LoadActivity,
                    "올바른 금액을 입력해주세요.",
                    Toast.LENGTH_SHORT
                ).show()
            }

            // 올바른 금액이 입력되었다면,
            else {
                // 서버 통신
                val retrofitObj = RetrofitInterface.create()
                // POST 호출 시 보낼 body 생성
                val loadRequest = LoadRequest(edtCharge.getText().toString().toInt())

                // API 호출
                retrofitObj.postCredit(authorization, loadRequest).enqueue(object: Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {

                        //// 통신 및 호출 성공 시
                        if(response.isSuccessful) {
                            /// 충전 완료 알림 띄운 후, MainActivity 전환
                            Toast.makeText(this@LoadActivity, "충전했습니다!", Toast.LENGTH_LONG)
                                .show()

                            // MainActivity 전환
                            val toMainIntent = Intent(this@LoadActivity, MainActivity::class.java)
                            toMainIntent.putExtra("authorization", authorization)
                            startActivity(toMainIntent)
                        }

                        //// 통신 성공, 호출 실패 시
                        else {
                            val responseErrorBody = JSONTokener(response.errorBody()?.string()!!).nextValue() as JSONObject
                            val errorCode = responseErrorBody.getString("errorCode")
                            Toast.makeText(this@LoadActivity, handleErrorCode(errorCode), Toast.LENGTH_SHORT)
                                .show()

                            // MainActivity 전환
                            val toMainIntent = Intent(this@LoadActivity, MainActivity::class.java)
                            toMainIntent.putExtra("authorization", authorization)
                            startActivity(toMainIntent)
                        }
                    }

                    //// 통신 실패 시
                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Toast.makeText(this@LoadActivity, "서버와 연결할 수 없습니다.", Toast.LENGTH_SHORT)
                            .show()

                        // MainActivity 전환
                        val toMainIntent = Intent(this@LoadActivity, MainActivity::class.java)
                        toMainIntent.putExtra("authorization", authorization)
                        startActivity(toMainIntent)
                    }
                })
            }
        }
    }

    // back 버튼 클릭 시, MainActivity 전환
    override fun onBackPressed() {
        Toast.makeText(this@LoadActivity, "잔액 충전을 취소합니다.",
            Toast.LENGTH_LONG).show()

        // MainActivity 전환
        val toMainIntent = Intent(this@LoadActivity, MainActivity::class.java)
        toMainIntent.putExtra("authorization", authorization)
        startActivity(toMainIntent)
    }
}