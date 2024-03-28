package com.maksk993.cryptoportfolio.view.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.maksk993.cryptoportfolio.R;
import com.maksk993.cryptoportfolio.contract.MainContract;
import com.maksk993.cryptoportfolio.model.models.FindFragmentById;
import com.maksk993.cryptoportfolio.presenter.MainPresenter;
import com.maksk993.cryptoportfolio.view.fragments.*;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements MainContract.MainView {
    FragmentManager fragmentManager = getSupportFragmentManager();
    Map<String, Fragment> fragmentMap = new HashMap<>();
    MainContract.MainPresenter presenter = new MainPresenter(this);
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        fragmentMap.put("History", new HistoryFragment());
        fragmentMap.put("Portfolio", new PortfolioFragment());
        fragmentMap.put("Settings", new SettingsFragment());

        bottomNavigationView = findViewById(R.id.bottom_nav_menu);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                int fragmentId;

                if (id == R.id.nav_history) fragmentId = FindFragmentById.getInteger(FindFragmentById.HISTORY);
                else if (id == R.id.nav_portfolio) fragmentId = FindFragmentById.getInteger(FindFragmentById.PORTFOLIO);
                else fragmentId = FindFragmentById.getInteger(FindFragmentById.SETTINGS);

                presenter.onClick(fragmentId);
                return true;
            }
        });
    }

    @Override
    public void startNewFragment(FindFragmentById id) {
        Fragment fragment;
        switch (id){
            case HISTORY:
                fragment = fragmentMap.get("History");
                break;
            case PORTFOLIO:
                fragment = fragmentMap.get("Portfolio");
                break;
            default:
                fragment = fragmentMap.get("Settings");
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.commit();
    }
}