package com.maksk993.cryptoportfolio.presentation.models;

import androidx.fragment.app.Fragment;

import com.maksk993.cryptoportfolio.presentation.view.fragments.AddAssetFragment;
import com.maksk993.cryptoportfolio.presentation.view.fragments.HistoryFragment;
import com.maksk993.cryptoportfolio.presentation.view.fragments.PortfolioFragment;
import com.maksk993.cryptoportfolio.presentation.view.fragments.SettingsFragment;

import java.util.HashMap;
import java.util.Map;

public enum FindFragmentById {
    PORTFOLIO, HISTORY, SETTINGS, ADD_ASSET;
    private static final Map<FindFragmentById, Fragment> fragmentMap = new HashMap<>();

    static {
        fragmentMap.put(HISTORY, new HistoryFragment());
        fragmentMap.put(PORTFOLIO, new PortfolioFragment());
        fragmentMap.put(SETTINGS, new SettingsFragment());
        fragmentMap.put(ADD_ASSET, new AddAssetFragment());
    }

    static public Fragment getFragment(FindFragmentById id){
        return fragmentMap.get(id);
    }

    static public int getInteger(FindFragmentById fragment){
        switch (fragment){
            case PORTFOLIO:
                return 0;
            case HISTORY:
                return 1;
            case SETTINGS:
                return 2;
            case ADD_ASSET:
                return 3;
            default:
                throw new IllegalArgumentException("Illegal value.");
        }
    }

    public int getInteger(){
        switch (this){
            case PORTFOLIO:
                return 0;
            case HISTORY:
                return 1;
            case SETTINGS:
                return 2;
            case ADD_ASSET:
                return 3;
            default:
                throw new IllegalArgumentException("Illegal value.");
        }
    }
}
