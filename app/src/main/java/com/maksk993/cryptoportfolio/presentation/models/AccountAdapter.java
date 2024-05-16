package com.maksk993.cryptoportfolio.presentation.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maksk993.cryptoportfolio.R;
import com.maksk993.cryptoportfolio.domain.models.Account;
import com.maksk993.cryptoportfolio.domain.models.AssetItem;

import java.util.List;

class AccountAdapterJ extends RecyclerView.Adapter<AssetViewHolder> {
    private OnItemClickListener listener;
    private Context context;
    private List<Account> items;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public AccountAdapterJ(Context context, List<Account> items) {
        this.context = context;
        this.items = items;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public AssetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AssetViewHolder(LayoutInflater.from(context).inflate(R.layout.asset_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AssetViewHolder holder, int position) {
        holder.assetName.setText(items.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
