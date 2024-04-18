package com.maksk993.cryptoportfolio.presentation.models;

import androidx.fragment.app.Fragment;

import com.maksk993.cryptoportfolio.presentation.view.fragments.AddAssetFragment;
import com.maksk993.cryptoportfolio.presentation.view.fragments.HistoryFragment;
import com.maksk993.cryptoportfolio.presentation.view.fragments.IndicateQuantityFragment;
import com.maksk993.cryptoportfolio.presentation.view.fragments.PortfolioFragment;
import com.maksk993.cryptoportfolio.presentation.view.fragments.SettingsFragment;

import java.util.HashMap;
import java.util.Map;

public enum FindFragmentById {
    PORTFOLIO(0), HISTORY(1), SETTINGS(2), ADD_ASSET(3), INDICATE_QUANTITY(4);

    private final int id;

    FindFragmentById(int id) {
        this.id = id;
    }
    private static final Map<FindFragmentById, Fragment> fragmentMap = new HashMap<>();

    static {
        fragmentMap.put(HISTORY, new HistoryFragment());
        fragmentMap.put(PORTFOLIO, new PortfolioFragment());
        fragmentMap.put(SETTINGS, new SettingsFragment());
        fragmentMap.put(ADD_ASSET, new AddAssetFragment());
        fragmentMap.put(INDICATE_QUANTITY, new IndicateQuantityFragment());
    }

    static public Fragment getFragment(FindFragmentById id){
        return fragmentMap.get(id);
    }

    public int getId(){
        return id;
    }
}
