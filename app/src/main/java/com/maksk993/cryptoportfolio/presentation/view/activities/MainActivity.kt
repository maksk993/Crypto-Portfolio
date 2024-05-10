package com.maksk993.cryptoportfolio.presentation.view.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.maksk993.cryptoportfolio.R
import com.maksk993.cryptoportfolio.databinding.ActivityMainBinding
import com.maksk993.cryptoportfolio.presentation.models.FindFragmentById
import com.maksk993.cryptoportfolio.presentation.viewmodel.MainViewModel
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
            startReceivingData()
            getAddedAssetsFromDb()
            getTransactionsFromDb()
            setSavedAppTheme()
        }

        initBottomNavMenu()
        initObservers()
    }

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
    }

    private fun initBottomNavMenu(){
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
    }
}