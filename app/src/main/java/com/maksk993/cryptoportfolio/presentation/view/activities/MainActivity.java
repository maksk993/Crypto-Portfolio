package com.maksk993.cryptoportfolio.presentation.view.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.MenuItem;

import com.maksk993.cryptoportfolio.R;
import com.maksk993.cryptoportfolio.presentation.contract.MainContract;
import com.maksk993.cryptoportfolio.presentation.models.FindFragmentById;
import com.maksk993.cryptoportfolio.presentation.presenter.MainPresenter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.maksk993.cryptoportfolio.presentation.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {
    MainViewModel viewModel;
    FragmentManager fragmentManager = getSupportFragmentManager();
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        bottomNavigationView = findViewById(R.id.bottom_nav_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                int fragmentId;

                if (id == R.id.nav_history) fragmentId = FindFragmentById.getInteger(FindFragmentById.HISTORY);
                else if (id == R.id.nav_portfolio) fragmentId = FindFragmentById.getInteger(FindFragmentById.PORTFOLIO);
                else fragmentId = FindFragmentById.getInteger(FindFragmentById.SETTINGS);

                viewModel.openFragment(fragmentId);
                return true;
            }
        });

        viewModel.shouldNewFragmentBeOpened().observe(this, new Observer<FindFragmentById>() {
            @Override
            public void onChanged(FindFragmentById findFragmentById) {
                Fragment fragment = FindFragmentById.getFragment(findFragmentById);
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragment);
                transaction.commit();
            }
        });
    }
}