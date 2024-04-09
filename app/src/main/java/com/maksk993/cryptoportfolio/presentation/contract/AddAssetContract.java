package com.maksk993.cryptoportfolio.presentation.contract;

import com.maksk993.cryptoportfolio.presentation.models.FindFragmentById;

public interface AddAssetContract {
    interface AddAssetView {
        void startNewFragment(FindFragmentById id);
    }

    interface AddAssetPresenter {
        void onClick(int id);
    }
}
