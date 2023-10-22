package com.example.c9pay.retrofit

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface RetrofitInterface {
    @POST("api/login")
    @Headers("accept: application/json",
        "content-type: application/json")
    fun postLogin(
        @Body req: LoginRequest
    ): Call<LoginResponse>

    @POST("api/user/signup")
    fun postSignup(
        @Body req: SignupRequest
    ): Call<String>

    @GET("api/user")
    fun getUser(
        @Header("Cookie") authorization: String?
    ): Call<UserResponse>

    @GET("api/qr")
    fun getQR(
        @Header("Cookie") authorization: String?
    ): Call<PayResponse>

    @POST("api/credit")
    fun postCredit(
        @Header("Cookie") authorization: String?,
        @Body req: LoadRequest
    ): Call<Void>


    companion object {
        private const val BASE_URL = "" //
        fun create(): RetrofitInterface {
            val gson: Gson = GsonBuilder().setLenient().create()


            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(RetrofitInterface::class.java)
        }
    }
}