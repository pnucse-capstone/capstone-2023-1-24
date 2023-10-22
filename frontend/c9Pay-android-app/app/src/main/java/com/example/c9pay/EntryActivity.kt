package com.example.c9pay

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.c9pay.module.handleErrorCode
import com.example.c9pay.retrofit.LoginRequest
import com.example.c9pay.retrofit.RetrofitInterface
import com.example.c9pay.retrofit.LoginResponse
import org.json.JSONObject
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EntryActivity: AppCompatActivity() {
    /* EntryActivity <-> MainActivity */
    companion object {
        var entryActivity: EntryActivity? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)

        /* EntryActivity <-> MainActivity */
        entryActivity = this
        MainActivity.mainActivity?.finish()     // MainActivity 종료

        // View 변수 선언 및 연결
        val edtID: EditText = findViewById(R.id.edtID)
        val edtPassword: EditText = findViewById(R.id.edtPassword)
        val btnLogin: Button = findViewById(R.id.btnLogin)
        val btnSignup: Button = findViewById(R.id.btnSignup)

        // 로그인 버튼 클릭 이벤트
        btnLogin.setOnClickListener {
            // 로그인 양식이 다 채워지지 않았으면,
            if (edtID.text.toString().isEmpty() || edtPassword.text.toString().isEmpty()) {
                Toast.makeText(
                    this@EntryActivity,
                    "ID와 비밀번호 모두 입력해주세요.",
                    Toast.LENGTH_SHORT
                ).show()
            }

            // 양식이 다 채워졌다면,
            else {
                // 서버 통신
                val retrofitObj = RetrofitInterface.create()
                // POST 호출 시 보낼 body 생성
                val loginRequest = LoginRequest(edtID.getText().toString(), edtPassword.getText().toString())

                // API 호출
                retrofitObj.postLogin(loginRequest).enqueue(object: Callback<LoginResponse> {
                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {

                        //// 통신 및 호출 성공 시
                        if(response.isSuccessful) {
                            // Body 받아오기
                            val responseBody = response.body()!!

                            /// 환영 메시지 띄운 후, MainActivity 전환
                            Toast.makeText(this@EntryActivity, "안녕하세요!", Toast.LENGTH_SHORT)
                                .show()
                            // Intent to MainActivity
                            val toMainIntent = Intent(this@EntryActivity, MainActivity::class.java)
                            toMainIntent.putExtra("authorizationOrigin", responseBody.token)
                            startActivity(toMainIntent)
                        }

                        //// 통신 성공, 호출 실패 시
                        else {
                            val responseErrorBody = JSONTokener(response.errorBody()?.string()!!).nextValue() as JSONObject
                            val errorCode = responseErrorBody.getString("errorCode")
                            Toast.makeText(this@EntryActivity, handleErrorCode(errorCode), Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                    //// 통신 실패 시
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(this@EntryActivity, "서버와 연결할 수 없습니다.", Toast.LENGTH_SHORT)
                            .show()
                    }
                })
            }
            return@setOnClickListener
        }

        // 회원가입 버튼 클릭 이벤트, SignupActivity 전환
        btnSignup.setOnClickListener {
            val toSignupIntent = Intent(this@EntryActivity, SignupActivity::class.java)
            startActivity(toSignupIntent)
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
                this@EntryActivity,
                "한 번 더 뒤로가기 버튼을 누르면 앱을 종료합니다.",
                Toast.LENGTH_LONG
            ).show()
        }
        backPressedTime = System.currentTimeMillis()
    }
}