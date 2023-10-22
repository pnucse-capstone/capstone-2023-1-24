package com.example.c9pay.retrofit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserResponse(
    @Expose
    @SerializedName(value = "username") val username: String,

    @Expose
    @SerializedName(value = "userId") val userId: String,

    @Expose
    @SerializedName(value = "email") val email: String,

    @Expose
    @SerializedName(value = "credit") val credit: Int
)