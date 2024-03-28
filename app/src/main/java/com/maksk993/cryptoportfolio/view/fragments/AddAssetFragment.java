package com.maksk993.cryptoportfolio.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.maksk993.cryptoportfolio.R;

public class AddAssetFragment extends Fragment {
    ImageButton btnBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_asset, container, false);

        btnBack = view.findViewById(R.id.btn_back);

       /* fragmentMap.put("Add asset", new AddAssetFragment());

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onClick(FindFragmentById.getInteger(FindFragmentById.ADD_ASSET));
            }
        });*/

        return view;
    }
}