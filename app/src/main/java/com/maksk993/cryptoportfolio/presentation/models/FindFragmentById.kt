package com.maksk993.cryptoportfolio.presentation.models

import androidx.fragment.app.Fragment
import com.maksk993.cryptoportfolio.presentation.screens.fragments.AccountManagementFragment
import com.maksk993.cryptoportfolio.presentation.screens.fragments.SearchAssetsFragment
import com.maksk993.cryptoportfolio.presentation.screens.fragments.AssetManagementFragment
import com.maksk993.cryptoportfolio.presentation.screens.fragments.HistoryFragment
import com.maksk993.cryptoportfolio.presentation.screens.fragments.AddingAssetFragment
import com.maksk993.cryptoportfolio.presentation.screens.fragments.PortfolioFragment
import com.maksk993.cryptoportfolio.presentation.screens.fragments.SettingsFragment

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
        fun init(){
            fragmentMap[PORTFOLIO] = PortfolioFragment()
            fragmentMap[HISTORY] = HistoryFragment()
            fragmentMap[SETTINGS] = SettingsFragment()
            fragmentMap[ADD_ASSET] = SearchAssetsFragment()
            fragmentMap[INDICATE_QUANTITY] = AddingAssetFragment()
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
                ADD_ASSET -> SearchAssetsFragment()
                INDICATE_QUANTITY -> AddingAssetFragment()
                ASSET_MANAGEMENT -> AssetManagementFragment()
                ACCOUNT_MANAGEMENT -> AccountManagementFragment()
            }
        }
    }
}
