package com.maksk993.cryptoportfolio.contract;

import com.maksk993.cryptoportfolio.model.models.FindFragmentById;

public interface MainContract {
    interface MainView {
        void startNewFragment(FindFragmentById id);
    }

    interface MainPresenter {
        void onClick(int id);
    }
}
