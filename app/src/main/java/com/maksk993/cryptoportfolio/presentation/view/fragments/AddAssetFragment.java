package com.maksk993.cryptoportfolio.presentation.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.maksk993.cryptoportfolio.R;
import com.maksk993.cryptoportfolio.presentation.models.AssetAdapter;
import com.maksk993.cryptoportfolio.presentation.models.AssetItem;
import com.maksk993.cryptoportfolio.presentation.models.AssetViewHolder;
import com.maksk993.cryptoportfolio.presentation.models.FindFragmentById;
import com.maksk993.cryptoportfolio.presentation.viewmodel.AddAssetViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddAssetFragment extends Fragment {
    AddAssetViewModel viewModel;
    ImageButton btnBack;
    RecyclerView recyclerView;
    List<AssetItem> items = new ArrayList<>();
    RecyclerView.Adapter<AssetViewHolder> adapter;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(AddAssetViewModel.class);
        view = inflater.inflate(R.layout.fragment_add_asset, container, false);

        recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new AssetAdapter(view.getContext(), items);
        recyclerView.setAdapter(adapter);

        btnBack = view.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(view -> {
            viewModel.openFragment(FindFragmentById.getInteger(FindFragmentById.PORTFOLIO));
        });

        viewModel.getPricesFromCoinMarketCap();
        viewModel.shouldNewFragmentBeOpened().observe(getViewLifecycleOwner(), new Observer<FindFragmentById>() {
            @Override
            public void onChanged(FindFragmentById findFragmentById) {
                Fragment fragment = FindFragmentById.getFragment(findFragmentById);
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragment);
                transaction.commit();
            }
        });

        viewModel.getItem().observe(getViewLifecycleOwner(),
                assetItem -> updatePrices(assetItem.getName(), assetItem.getPrice())
        );

        return view;
    }

    public void updatePrices(String symbol, float price) {
        if (!items.isEmpty()) {
            for (int i = 0; i < items.size(); i++) {
                if (Objects.equals(items.get(i).getName(), symbol)) {
                    if (price != items.get(i).getPrice()) {
                        items.set(i, new AssetItem(symbol, price, R.drawable.ic_history));
                        adapter.notifyItemChanged(i);
                    }
                    return;
                }
            }
        }
        items.add(new AssetItem(symbol, price, R.drawable.ic_history));
        items.sort((a, b) -> a.getName().compareTo(b.getName()));
        int index = 0;
        for (int j = 0; j < items.size(); j++){
            if (items.get(j).getName().equals(symbol)) {
                index = j;
                break;
            }
        }
        adapter.notifyItemInserted(index);
    }
}