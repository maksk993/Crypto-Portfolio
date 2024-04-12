package com.maksk993.cryptoportfolio.presentation.presenter;

import com.maksk993.cryptoportfolio.data.repository.CryptoRepositoryImpl;
import com.maksk993.cryptoportfolio.domain.usecases.GetPricesFromCoinMarketCap;
import com.maksk993.cryptoportfolio.presentation.contract.AddAssetContract;
import com.maksk993.cryptoportfolio.presentation.models.FindFragmentById;

import java.lang.ref.WeakReference;

public class AddAssetPresenter implements AddAssetContract.AddAssetPresenter {

    WeakReference<AddAssetContract.AddAssetView> view;
    GetPricesFromCoinMarketCap getPrices = new GetPricesFromCoinMarketCap(new CryptoRepositoryImpl(this));

    public AddAssetPresenter(AddAssetContract.AddAssetView view){
        this.view = new WeakReference<>(view);
    }

    @Override
    public void onClick(int id) {
        if (view.get() == null) return;
        for (FindFragmentById fragmentId : FindFragmentById.values()){
            if (fragmentId.getInteger() == id){
                view.get().startNewFragment(fragmentId);
                return;
            }
        }
    }

    @Override
    public void getPricesFromCoinMarketCap() {
        getPrices.execute();
    }

    @Override
    public void updatePrices(String symbol, float price) {
        view.get().updatePrices(symbol, price);
    }
}
