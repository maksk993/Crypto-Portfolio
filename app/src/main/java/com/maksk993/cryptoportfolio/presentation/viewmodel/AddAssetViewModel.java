package com.maksk993.cryptoportfolio.presentation.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.maksk993.cryptoportfolio.domain.repository.DataReceivedCallBack;
import com.maksk993.cryptoportfolio.data.repository.CryptoRepositoryImpl;
import com.maksk993.cryptoportfolio.domain.usecases.GetPricesFromCoinMarketCap;
import com.maksk993.cryptoportfolio.presentation.models.AssetItem;

public class AddAssetViewModel extends MainViewModel {
    private final CryptoRepositoryImpl repository = new CryptoRepositoryImpl();
    private final GetPricesFromCoinMarketCap getPrices = new GetPricesFromCoinMarketCap(repository);
    private MutableLiveData<AssetItem> item = new MutableLiveData<>();

    public void getPricesFromCoinMarketCap() {
        getPrices.execute(new DataReceivedCallBack() {
            @Override
            public void dataReceived(String symbol, float price) {
                item.setValue(new AssetItem(symbol, price));
            }
        });
    }

    public MutableLiveData<AssetItem> getItem(){
        return item;
    }
}
