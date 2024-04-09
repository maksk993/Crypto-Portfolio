package com.maksk993.cryptoportfolio.presentation.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maksk993.cryptoportfolio.R;

import java.util.List;

public class AssetAdapter extends RecyclerView.Adapter<AssetViewHolder> {
    Context context;
    List<AssetItem> items;

    public AssetAdapter(Context context, List<AssetItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public AssetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AssetViewHolder(LayoutInflater.from(context).inflate(R.layout.asset_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AssetViewHolder holder, int position) {
        holder.assetName.setText(items.get(position).getName());
        holder.assetPrice.setText(String.valueOf(items.get(position).getPrice() + " $"));
        holder.assetImage.setImageResource(items.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}