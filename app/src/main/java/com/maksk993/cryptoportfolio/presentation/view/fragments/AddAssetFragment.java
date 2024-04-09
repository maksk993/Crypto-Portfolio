package com.maksk993.cryptoportfolio.presentation.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.maksk993.cryptoportfolio.R;
import com.maksk993.cryptoportfolio.presentation.contract.AddAssetContract;
import com.maksk993.cryptoportfolio.presentation.models.AssetAdapter;
import com.maksk993.cryptoportfolio.presentation.models.AssetItem;
import com.maksk993.cryptoportfolio.presentation.models.FindFragmentById;
import com.maksk993.cryptoportfolio.presentation.presenter.AddAssetPresenter;

import java.util.ArrayList;
import java.util.List;

public class AddAssetFragment extends Fragment implements AddAssetContract.AddAssetView {
    ImageButton btnBack;
    RecyclerView recyclerView;
    AddAssetContract.AddAssetPresenter presenter = new AddAssetPresenter(this);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_asset, container, false);

        btnBack = view.findViewById(R.id.btn_back);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onClick(FindFragmentById.getInteger(FindFragmentById.PORTFOLIO));
            }
        });

        List<AssetItem> items = new ArrayList<>();
        for (int i = 0; i < 30; i++){
            items.add(new AssetItem("NEAR", 7.23f, R.drawable.ic_history));
        }

        recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new AssetAdapter(view.getContext(), items));

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