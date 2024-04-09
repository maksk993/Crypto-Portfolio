package com.maksk993.cryptoportfolio.presentation.view.fragments;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.maksk993.cryptoportfolio.R;
import com.maksk993.cryptoportfolio.presentation.contract.PortfolioContract;
import com.maksk993.cryptoportfolio.presentation.models.FindFragmentById;
import com.maksk993.cryptoportfolio.presentation.presenter.PortfolioPresenter;

public class PortfolioFragment extends Fragment implements PortfolioContract.PortfolioView {
    Button btnAddAsset;
    PortfolioContract.PortfolioPresenter presenter = new PortfolioPresenter(this);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_portfolio, container, false);

        btnAddAsset = view.findViewById(R.id.btn_add_asset);

        btnAddAsset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onClick(FindFragmentById.getInteger(FindFragmentById.ADD_ASSET));
            }
        });

        return view;
    }

    @Override
    public void startNewFragment(FindFragmentById id) {
        Fragment fragment = FindFragmentById.getFragment(id);
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.commit();
    }
}