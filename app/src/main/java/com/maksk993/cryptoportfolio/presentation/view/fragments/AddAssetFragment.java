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
import com.maksk993.cryptoportfolio.presentation.models.AssetViewHolder;
import com.maksk993.cryptoportfolio.presentation.models.FindFragmentById;
import com.maksk993.cryptoportfolio.presentation.presenter.AddAssetPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddAssetFragment extends Fragment implements AddAssetContract.AddAssetView {
    ImageButton btnBack;
    RecyclerView recyclerView;
    List<AssetItem> items = new ArrayList<>();
    RecyclerView.Adapter<AssetViewHolder> adapter;

    View view;
    AddAssetContract.AddAssetPresenter presenter = new AddAssetPresenter(this);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_add_asset, container, false);

        recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new AssetAdapter(view.getContext(), items);
        recyclerView.setAdapter(adapter);

        btnBack = view.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onClick(FindFragmentById.getInteger(FindFragmentById.PORTFOLIO));
            }
        });

        presenter.getPricesFromCoinMarketCap();

        return view;
    }

    @Override
    public void updatePrices(String symbol, float price) {
        if (!items.isEmpty()) {
            for (int i = 0; i < items.size(); i++) {
                if (Objects.equals(items.get(i).getName(), symbol)) {
                    return;
                }
            }
        }
        items.add(new AssetItem(symbol, price, R.drawable.ic_history));
        adapter.notifyItemInserted(items.size() - 1);
    }

    @Override
    public void startNewFragment(FindFragmentById id) {
        Fragment fragment = FindFragmentById.getFragment(id);
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.commit();
    }
}