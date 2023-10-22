package com.example.c9pay.module

fun handleErrorCode(errorCodeStr: String): String {
    val errorCode: Int = errorCodeStr.toInt()
    when (errorCode) {
        100 -> {
            return "이미 등록된 ID 입니다."
        }
        101 -> {
            return "세션이 만료되었습니다. 다시 로그인 해주세요."
        }
        102 -> {
            return "등록되지 않은 회원입니다."
        }
        103 -> {
            return "비밀번호가 올바르지 않습니다."
        }
        104 -> {
            return "입력한 정보가 누락되었거나, 형식이 올바르지 않습니다."
        }
        199 -> {
            return "알 수 없는 서버 오류입니다."
        }
        else -> {
            return "알 수 없는 오류입니다."
        }
    }
}