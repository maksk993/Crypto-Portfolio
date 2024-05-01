package com.maksk993.cryptoportfolio.data.repository

import android.util.Log
import com.maksk993.cryptoportfolio.data.models.retrofit.CoinMarketCapAPI
import com.maksk993.cryptoportfolio.data.models.retrofit.CryptoCurrencies
import com.maksk993.cryptoportfolio.domain.models.DataReceivedCallBack
import com.maksk993.cryptoportfolio.domain.repository.CryptoRepository
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class CryptoRepositoryImpl : CryptoRepository {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://pro-api.coinmarketcap.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val API_KEY : String = "API_KEY"
    companion object {
        val actualPrices: MutableMap<String, Float> = HashMap()
    }

    override fun getData(callBack : DataReceivedCallBack) {
        val cmcApi : CoinMarketCapAPI = retrofit.create(CoinMarketCapAPI::class.java)

        for (currency in CryptoCurrencies.entries){
            val id = currency.coinMarketCapId
            val call : Call<ResponseBody> = cmcApi.getCurrencyInfo(API_KEY, id)

            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        try {
                            val jsonObject = JSONObject(response.body()!!.string())
                            val currencyData = jsonObject.getJSONObject("data").getJSONObject(id.toString())
                            val price = currencyData.getJSONObject("quote").getJSONObject("USD")
                                .getDouble("price").toFloat()
                            val symbol = currencyData.getString("symbol")
                            actualPrices[symbol] = price
                            callBack.dataReceived(symbol, price)
                            // Log.d("PRICES", currencyData.toString());
                        }
                        catch (e: JSONException) {
                            Log.d("PRICES", e.toString())
                        }
                        catch (e: IOException) {
                            Log.d("PRICES", e.toString())
                        }
                    }
                    else {
                        Log.d("PRICES", "response.isSuccessful() == false")
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.d("PRICES", "onFailure()")
                }
            })
        }
    }
}