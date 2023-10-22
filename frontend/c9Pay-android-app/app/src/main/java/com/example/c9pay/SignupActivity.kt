package com.example.c9pay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.c9pay.module.handleErrorCode
import com.example.c9pay.retrofit.RetrofitInterface
import com.example.c9pay.retrofit.SignupRequest
import org.json.JSONObject
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity: AppCompatActivity() {
    /* Stack on EntryActivity */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // View 변수 선언 및 연결
        val edtNewID: EditText = findViewById(R.id.edtNewID)
        val edtNewUsername: EditText = findViewById(R.id.edtNewUsername)
        val edtNewPassword: EditText = findViewById(R.id.edtNewPassword)
        val edtNewEmail:EditText = findViewById(R.id.edtNewEmail)
        val btnSignupSubmit: Button = findViewById(R.id.btnSignupSubmit)

        // 확인 버튼 클릭 이벤트
        btnSignupSubmit.setOnClickListener {
            // 회원가입 양식이 다 채워지지 않았으면,
            if (edtNewID.text.toString().isEmpty() || edtNewUsername.text.toString().isEmpty() ||
                edtNewPassword.text.toString().isEmpty() || edtNewEmail.text.toString().isEmpty()) {
                Toast.makeText(
                    this@SignupActivity,
                    "양식을 모두 입력해주세요.",
                    Toast.LENGTH_SHORT
                ).show()
            }

            // 양식이 다 채워졌다면,
            else {
                // 서버 통신
                val retrofitObj = RetrofitInterface.create()
                // POST 호출 시 보낼 body 생성
                val signupRequest = SignupRequest(edtNewID.getText().toString(), edtNewUsername.getText().toString(),
                    edtNewPassword.getText().toString(), edtNewEmail.getText().toString())

                // API 호출
                retrofitObj.postSignup(signupRequest).enqueue(object: Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {

                        //// 통신 및 호출 성공 시
                        if(response.isSuccessful) {
                            Toast.makeText(this@SignupActivity, "환영합니다!", Toast.LENGTH_SHORT)
                                .show()
                            finish()
                        }

                        //// 통신 성공 및 호출 실패 시
                        else {
                            val responseErrorBody = JSONTokener(response.errorBody()?.string()!!).nextValue() as JSONObject
                            val errorCode = responseErrorBody.getString("errorCode")
                            Toast.makeText(this@SignupActivity, handleErrorCode(errorCode), Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                    //// 통신 실패 시
                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Toast.makeText(this@SignupActivity, "서버와 연결할 수 없습니다.", Toast.LENGTH_SHORT)
                            .show()
                    }
                })
            }
            return@setOnClickListener
        }
    }

    // back 버튼 클릭 시, 이전 화면으로
    override fun onBackPressed() {
        Toast.makeText(this, "회원가입을 취소합니다.", Toast.LENGTH_SHORT).show()
        finish()
    }
}