package com.maksk993.cryptoportfolio.data.models.retrofit

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CryptoServiceAPI {
    @GET("v1/cryptocurrency/quotes/latest")
    fun getCurrencyInfo(@Header("X-CMC_PRO_API_KEY") apiKey: String, @Query("id") id: Int): Call<ResponseBody>
}