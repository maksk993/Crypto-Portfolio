package com.maksk993.cryptoportfolio.data.repository;

import android.util.Log;

import com.maksk993.cryptoportfolio.data.models.CoinMarketCapAPI;
import com.maksk993.cryptoportfolio.data.models.CryptoCurrencies;
import com.maksk993.cryptoportfolio.domain.repository.DataReceivedCallBack;
import com.maksk993.cryptoportfolio.domain.repository.CryptoRepository;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CryptoRepositoryImpl implements CryptoRepository {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://pro-api.coinmarketcap.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private static final String API_KEY = "API_KEY"; // Insert your key

    @Override
    public void getData(DataReceivedCallBack callBack) {
        CoinMarketCapAPI coinMarketCapApi = retrofit.create(CoinMarketCapAPI.class);

        for (CryptoCurrencies currency : CryptoCurrencies.values()) {
            int id = currency.getCoinMarketCapId();
            Call<ResponseBody> call = coinMarketCapApi.getCurrencyInfo(API_KEY, id);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            JSONObject currencyData = jsonObject.getJSONObject("data").getJSONObject(Integer.toString(id));
                            float price = (float) currencyData.getJSONObject("quote").getJSONObject("USD").getDouble("price");
                            String symbol = currencyData.getString("symbol");
                            callBack.dataReceived(symbol, price);
                            // Log.d("PRICES", currencyData.toString());
                        } catch (JSONException | IOException e) {
                            Log.d("PRICES", e.toString());
                        }
                    } else {
                        Log.d("PRICES", "response is not successful.");
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.d("PRICES", "response is not successful.");
                }
            });
        }
    }
}
