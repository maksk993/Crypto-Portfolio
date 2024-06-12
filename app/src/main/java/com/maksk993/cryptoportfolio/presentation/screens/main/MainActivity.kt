package com.maksk993.cryptoportfolio.presentation.screens.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.maksk993.cryptoportfolio.R
import com.maksk993.cryptoportfolio.databinding.ActivityMainBinding
import com.maksk993.cryptoportfolio.presentation.models.FindFragmentById
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.apply {
            setSavedAppTheme()
            getLastAccountName()
            getAddedAssetsFromDb()
            getTransactionsFromDb()
            getAccountsFromDb()
            startReceivingData()
        }

        initViews()
        initFragments()
        initObservers()
    }

    private fun initFragments() = FindFragmentById.init()

    private fun initObservers(){
        viewModel.nextFragment.observe(this) {
            val fragment = FindFragmentById.getFragment(it)
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.nav_host_fragment, fragment)
            transaction.commit()
        }

        viewModel.nightMode.observe(this){
            var isNightMode = false
            if (it == true){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.btnChangeAcc.setImageResource(R.drawable.ic_arrow_down)
                isNightMode = true
            }
            else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.btnChangeAcc.setImageResource(R.drawable.ic_arrow_down_night)
            }
            viewModel.setNightTheme(isNightMode)
        }

        viewModel.currentAccount.observe(this){
            binding.portfolioName.text = it.name
            viewModel.apply {
                getAddedAssetsFromDb()
                getTransactionsFromDb()
                getAccountsFromDb()
                setSavedAppTheme()
                createFragment(FindFragmentById.PORTFOLIO, FindFragmentById.HISTORY)
                openFragment(FindFragmentById.PORTFOLIO)
                calculateBalance()
            }
        }
    }

    private fun initViews(){
        binding.bottomNavMenu.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener
        {
            val id = it.itemId
            val fragmentId: FindFragmentById =
                when (id) {
                    R.id.nav_history -> FindFragmentById.HISTORY
                    R.id.nav_portfolio -> FindFragmentById.PORTFOLIO
                    else -> FindFragmentById.SETTINGS
                }
            viewModel.openFragment(fragmentId)
            true
        })

        binding.btnChangeAcc.setOnClickListener{
            if (viewModel.nextFragment.value == FindFragmentById.ACCOUNT_MANAGEMENT)
                viewModel.openFragment(FindFragmentById.PORTFOLIO)
            else
                viewModel.openFragment(FindFragmentById.ACCOUNT_MANAGEMENT)
        }
    }

    override fun onBackPressed() {
        if (viewModel.nextFragment.value != FindFragmentById.PORTFOLIO)
            viewModel.openFragment(FindFragmentById.PORTFOLIO)
        else
            super.onBackPressed()
    }
}