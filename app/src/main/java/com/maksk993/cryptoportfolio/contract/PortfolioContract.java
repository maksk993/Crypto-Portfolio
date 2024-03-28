package com.maksk993.cryptoportfolio.contract;

import com.maksk993.cryptoportfolio.model.models.FindFragmentById;

public interface PortfolioContract {
    interface PortfolioView {
        void startNewFragment(FindFragmentById id);
    }

    interface PortfolioPresenter {
        void onClick(int id);
    }
}
