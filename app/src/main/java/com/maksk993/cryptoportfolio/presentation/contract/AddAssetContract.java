package com.maksk993.cryptoportfolio.presentation.contract;

import com.maksk993.cryptoportfolio.presentation.models.FindFragmentById;

public interface AddAssetContract {
    interface AddAssetView {
        void startNewFragment(FindFragmentById id);
        void updatePrices(String symbol, float price);
    }

    interface AddAssetPresenter {
        void onClick(int id);
        void getPricesFromCoinMarketCap();
        void updatePrices(String symbol, float price);
    }
}
