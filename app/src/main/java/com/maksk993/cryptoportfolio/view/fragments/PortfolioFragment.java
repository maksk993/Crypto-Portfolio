package com.maksk993.cryptoportfolio.view.fragments;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.maksk993.cryptoportfolio.R;
import com.maksk993.cryptoportfolio.contract.PortfolioContract;
import com.maksk993.cryptoportfolio.model.models.FindFragmentById;
import com.maksk993.cryptoportfolio.presenter.PortfolioPresenter;

import java.util.HashMap;
import java.util.Map;

public class PortfolioFragment extends Fragment implements PortfolioContract.PortfolioView {
    Button btnAddAsset;
    Map<String, Fragment> fragmentMap = new HashMap<>();
    PortfolioContract.PortfolioPresenter presenter = new PortfolioPresenter(this);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_portfolio, container, false);

        btnAddAsset = view.findViewById(R.id.btn_add_asset);

        fragmentMap.put("Add asset", new AddAssetFragment());

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
        Fragment fragment;
        switch (id){
            case ADD_ASSET:
                fragment = fragmentMap.get("Add asset");
                break;
            default:
                fragment = fragmentMap.get("Settings");
        }
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.commit();
    }
}