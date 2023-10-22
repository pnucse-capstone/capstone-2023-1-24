package com.example.c9pay.retrofit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PayResponse(
    @Expose
    @SerializedName(value = "content") val content: String,

    @Expose
    @SerializedName(value = "expiredAt") val expiredAt: Long,

    @Expose
    @SerializedName(value = "sign") val sign: String
)