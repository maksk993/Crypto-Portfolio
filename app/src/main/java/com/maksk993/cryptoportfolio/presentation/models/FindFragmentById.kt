package com.maksk993.cryptoportfolio.presentation.models

import androidx.fragment.app.Fragment
import com.maksk993.cryptoportfolio.presentation.view.fragments.AccountManagementFragment
import com.maksk993.cryptoportfolio.presentation.view.fragments.AddAssetFragment
import com.maksk993.cryptoportfolio.presentation.view.fragments.AssetManagementFragment
import com.maksk993.cryptoportfolio.presentation.view.fragments.HistoryFragment
import com.maksk993.cryptoportfolio.presentation.view.fragments.IndicateQuantityFragment
import com.maksk993.cryptoportfolio.presentation.view.fragments.PortfolioFragment
import com.maksk993.cryptoportfolio.presentation.view.fragments.SettingsFragment

val fragmentMap = HashMap<FindFragmentById, Fragment>()

enum class FindFragmentById(val id : Int) {
    PORTFOLIO(0),
    HISTORY(1),
    SETTINGS(2),
    ADD_ASSET(3),
    INDICATE_QUANTITY(4),
    ASSET_MANAGEMENT(5),
    ACCOUNT_MANAGEMENT(6);

    companion object {
        init {
            fragmentMap[PORTFOLIO] = PortfolioFragment()
            fragmentMap[HISTORY] = HistoryFragment()
            fragmentMap[SETTINGS] = SettingsFragment()
            fragmentMap[ADD_ASSET] = AddAssetFragment()
            fragmentMap[INDICATE_QUANTITY] = IndicateQuantityFragment()
            fragmentMap[ASSET_MANAGEMENT] = AssetManagementFragment()
            fragmentMap[ACCOUNT_MANAGEMENT] = AccountManagementFragment()
        }

        fun getFragment(id : FindFragmentById) : Fragment {
            return fragmentMap[id] ?: PortfolioFragment()
        }

        fun newInstance(id : FindFragmentById){
            fragmentMap[id] = when(id){
                PORTFOLIO -> PortfolioFragment()
                HISTORY -> HistoryFragment()
                SETTINGS -> SettingsFragment()
                ADD_ASSET -> AddAssetFragment()
                INDICATE_QUANTITY -> IndicateQuantityFragment()
                ASSET_MANAGEMENT -> AssetManagementFragment()
                ACCOUNT_MANAGEMENT -> AccountManagementFragment()
            }
        }
    }
}
