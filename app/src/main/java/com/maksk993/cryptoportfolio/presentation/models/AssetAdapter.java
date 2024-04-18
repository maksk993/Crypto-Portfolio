package com.maksk993.cryptoportfolio.presentation.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maksk993.cryptoportfolio.R;
import com.maksk993.cryptoportfolio.domain.models.AssetItem;

import java.util.List;

public class AssetAdapter extends RecyclerView.Adapter<AssetViewHolder> {
    private Context context;
    private List<AssetItem> items;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

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
        holder.assetName.setText(items.get(position).getSymbol());
        if (items.get(position).getPrice() < 1.f) {
            holder.assetPrice.setText(String.format("%.4f", items.get(position).getPrice()) + " $");
        }
        else if (items.get(position).getPrice() < 10.f) {
            holder.assetPrice.setText(String.format("%.3f", items.get(position).getPrice()) + " $");
        }
        else {
            holder.assetPrice.setText(String.format("%.2f", items.get(position).getPrice()) + " $");
        }
        holder.assetImage.setImageResource(items.get(position).getImage());
        holder.itemView.setOnClickListener(view -> {
            if (listener != null) {
                listener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
