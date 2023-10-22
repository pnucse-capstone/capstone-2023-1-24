package com.example.c9pay.retrofit

data class SignupRequest(
    val userId: String,
    val username: String,
    val password: String,
    val email: String
)