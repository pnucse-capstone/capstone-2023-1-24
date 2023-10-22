package com.example.c9pay.retrofit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @Expose
    @SerializedName(value = "token") val token: String
)