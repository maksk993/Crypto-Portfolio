package com.maksk993.cryptoportfolio.data.models;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface CoinMarketCapAPI {
    @GET("v1/cryptocurrency/quotes/latest")
    Call<ResponseBody> getCurrencyInfo(@Header("X-CMC_PRO_API_KEY") String apiKey, @Query("id") int id);
}
