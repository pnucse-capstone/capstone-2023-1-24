package com.example.c9pay

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.c9pay.module.handleErrorCode
import com.example.c9pay.retrofit.RetrofitInterface
import com.example.c9pay.retrofit.UserResponse
import org.json.JSONObject
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat

class MainActivity: AppCompatActivity() {
    /* MainActivity <-> EntryActivity
    * MainActivity <-> PayActivity
    * MainActivity <-> LoadActivity */
    companion object {
        var mainActivity: MainActivity? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /* MainActivity <-> EntryActivity
        * MainActivity <-> PayActivity
        * MainActivity <-> LoadActivity */
        mainActivity = this
        EntryActivity.entryActivity?.finish()
        PayActivity.payActivity?.finish()
        // LoadActivity.loadActivity?.finish()

        // get Token from Intent
        val authorizationOrigin: String? = intent.getStringExtra("authorizationOrigin")
        val authorization: String?
        if (authorizationOrigin == null) {
            authorization = intent.getStringExtra("authorization")
        }
        else {
            authorization = "Authorization=$authorizationOrigin"  // header 양식에 맞게 parsing
        }

        // View 변수 선언 및 연결
        val txtUserName: TextView = findViewById(R.id.txtUserName)
        val txtCredit: TextView = findViewById(R.id.txtCredit)
        val btnSignout: Button = findViewById(R.id.btnSignout)
        val btnPay: Button = findViewById(R.id.btnPay)
        val btnLoad: Button = findViewById(R.id.btnLoad)

        // 서버 통신
        val retrofitObj = RetrofitInterface.create()
        // API 호출
        retrofitObj.getUser(authorization).enqueue(object: Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {

                //// 통신 및 호출 성공 시
                if(response.isSuccessful) {
                    // Body 받아오기
                    val responseBody = response.body()!!

                    // 사용자 이름 받아오기 및 환영 메시지 출력
                    txtUserName.setText("안녕하세요,\n" + responseBody.username + " 님!")
                    // 사용자 현재 잔액 출력
                    val credit = responseBody.credit
                    val t_dec_up = DecimalFormat("#,###")
                    val credit_decimal = t_dec_up.format(credit)
                    txtCredit.setText("₩ " + credit_decimal)
                }

                //// 통신 성공, 호출 실패 시
                else {
                    val responseErrorBody = JSONTokener(response.errorBody()?.string()!!).nextValue() as JSONObject
                    val errorCode = responseErrorBody.getString("errorCode")
                    Toast.makeText(this@MainActivity, handleErrorCode(errorCode), Toast.LENGTH_SHORT)
                        .show()

                    // EntryActivity 전환
                    val toEntryIntent = Intent(this@MainActivity, EntryActivity::class.java)
                    startActivity(toEntryIntent)
                }
            }

            //// 통신 실패 시
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "서버와 연결할 수 없습니다.", Toast.LENGTH_SHORT)
                    .show()

                // EntryActivity 전환
                val toEntryIntent = Intent(this@MainActivity, EntryActivity::class.java)
                startActivity(toEntryIntent)
            }
        })

        // 충전하기 버튼 클릭 이벤트, LoadActivity 전환
        btnLoad.setOnClickListener {
            val toLoadIntent = Intent(this@MainActivity,LoadActivity::class.java)
            toLoadIntent.putExtra("authorization", authorization)
            startActivity(toLoadIntent)
        }

        // 결제하기 버튼 클릭 이벤트, PayActivity 전환
        btnPay.setOnClickListener {
            val toPayIntent = Intent(this@MainActivity,PayActivity::class.java)
            toPayIntent.putExtra("authorization", authorization)
            startActivity(toPayIntent)
        }

        // 로그아웃 버튼 클릭 이벤트, EntryActivity 전환
        btnSignout.setOnClickListener {
            Toast.makeText(this@MainActivity, "로그아웃 했습니다.",
                Toast.LENGTH_LONG).show()

            val toEntryIntent = Intent(this@MainActivity, EntryActivity::class.java)
            startActivity(toEntryIntent)
        }

    }

    // back 버튼 더블 클릭 시, App 종료
    var backPressedTime: Long = 0
    override fun onBackPressed() {
        if (backPressedTime + 3000 > System.currentTimeMillis()) {
            super.onBackPressed()
            finish()
        }
        else {
            Toast.makeText(
                this@MainActivity,
                "한 번 더 뒤로가기 버튼을 누르면 앱을 종료합니다.",
                Toast.LENGTH_LONG
            ).show()
        }
        backPressedTime = System.currentTimeMillis()
    }
}