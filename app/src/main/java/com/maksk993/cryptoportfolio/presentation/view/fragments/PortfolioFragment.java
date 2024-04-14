package com.maksk993.cryptoportfolio.presentation.view.fragments;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.maksk993.cryptoportfolio.R;
import com.maksk993.cryptoportfolio.presentation.models.FindFragmentById;
import com.maksk993.cryptoportfolio.presentation.viewmodel.MainViewModel;

public class PortfolioFragment extends Fragment {
    MainViewModel viewModel;
    Button btnAddAsset;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_portfolio, container, false);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        btnAddAsset = view.findViewById(R.id.btn_add_asset);

        btnAddAsset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.openFragment(FindFragmentById.getInteger(FindFragmentById.ADD_ASSET));
            }
        });

        viewModel.shouldNewFragmentBeOpened().observe(getViewLifecycleOwner(), new Observer<FindFragmentById>() {
            @Override
            public void onChanged(FindFragmentById findFragmentById) {
                Fragment fragment = FindFragmentById.getFragment(findFragmentById);
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragment);
                transaction.commit();
            }
        });

        return view;
    }
}