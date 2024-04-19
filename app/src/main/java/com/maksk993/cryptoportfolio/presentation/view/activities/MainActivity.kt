package com.maksk993.cryptoportfolio.presentation.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.maksk993.cryptoportfolio.R
import com.maksk993.cryptoportfolio.data.models.room.Database
import com.maksk993.cryptoportfolio.databinding.ActivityMainBinding
import com.maksk993.cryptoportfolio.presentation.models.FindFragmentById
import com.maksk993.cryptoportfolio.presentation.viewmodel.MainViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel : MainViewModel
    private lateinit var binding : ActivityMainBinding
    private val fragmentManager : FragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Database.init(applicationContext)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.startReceivingData()
        viewModel.getAssetsFromPortfolio()

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

        viewModel.shouldNewFragmentBeOpened().observe(this) {
            val fragment = FindFragmentById.getFragment(it)
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.nav_host_fragment, fragment)
            transaction.commit()
        }
    }
}