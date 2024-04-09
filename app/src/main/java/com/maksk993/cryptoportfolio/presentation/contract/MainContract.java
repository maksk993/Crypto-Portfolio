package com.maksk993.cryptoportfolio.presentation.contract;

import com.maksk993.cryptoportfolio.presentation.models.FindFragmentById;

public interface MainContract {
    interface MainView {
        void startNewFragment(FindFragmentById id);
    }

    interface MainPresenter {
        void onClick(int id);
    }
}
