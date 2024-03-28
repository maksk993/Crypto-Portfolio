package com.maksk993.cryptoportfolio.presenter;

import com.maksk993.cryptoportfolio.contract.PortfolioContract;
import com.maksk993.cryptoportfolio.model.models.FindFragmentById;

import java.lang.ref.WeakReference;

public class PortfolioPresenter implements PortfolioContract.PortfolioPresenter {
    WeakReference<PortfolioContract.PortfolioView> view;

    public PortfolioPresenter(PortfolioContract.PortfolioView view){
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
}
