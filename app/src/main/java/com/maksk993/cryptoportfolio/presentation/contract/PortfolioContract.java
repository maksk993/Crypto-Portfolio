package com.maksk993.cryptoportfolio.presentation.contract;

import com.maksk993.cryptoportfolio.presentation.models.FindFragmentById;

public interface PortfolioContract {
    interface PortfolioView {
        void startNewFragment(FindFragmentById id);
    }

    interface PortfolioPresenter {
        void onClick(int id);
    }
}
