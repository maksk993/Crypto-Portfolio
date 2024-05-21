package com.maksk993.cryptoportfolio.data.repository

import android.util.Log
import com.maksk993.cryptoportfolio.BuildConfig
import com.maksk993.cryptoportfolio.data.models.retrofit.CryptoServiceAPI
import com.maksk993.cryptoportfolio.domain.models.DataReceivedCallBack
import com.maksk993.cryptoportfolio.domain.repository.CryptoRepository
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class CryptoRepositoryImpl : CryptoRepository {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://pro-api.coinmarketcap.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val apiKey: String = BuildConfig.API_KEY
    private val service: CryptoServiceAPI = retrofit.create(CryptoServiceAPI::class.java)

    companion object {
        val actualPrices: MutableMap<String, Float> = HashMap()
    }

    override fun getPrices(callBack : DataReceivedCallBack) {
        val call = service.getPrices(apiKey)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val dataArray = JSONObject(response.body()!!.string()).getJSONArray("data")
                    for (i in 0 until dataArray.length()) {
                        val asset = dataArray.getJSONObject(i)
                        val symbol = asset.getString("symbol")
                        val price = asset.getJSONObject("quote")
                            .getJSONObject("USD").getDouble("price").toFloat()
                        actualPrices[symbol] = price
                        callBack.dataReceived(symbol, price)
                    }
                }
                else Log.d("PRICES", "Response is not successful")
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("PRICES", "onFailure()")
            }
        })
    }
}