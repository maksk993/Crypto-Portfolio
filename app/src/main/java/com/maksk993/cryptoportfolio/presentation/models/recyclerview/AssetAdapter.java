package com.maksk993.cryptoportfolio.presentation.models.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maksk993.cryptoportfolio.R;
import com.maksk993.cryptoportfolio.domain.models.Asset;

import java.util.List;

public class AssetAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private Context context;
    private List<Asset> items;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public AssetAdapter(Context context, List<Asset> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_view_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.leftName.setText(items.get(position).getSymbol());
        if (items.get(position).getPrice() == 0.f){
            holder.rightText.setText("0.00 $");
        }
        else if (items.get(position).getPrice() < 0.f) {
            holder.rightText.setText("???");
        }
        else if (items.get(position).getPrice() < 1.f) {
            holder.rightText.setText(String.format("%.4f", items.get(position).getPrice()) + " $");
        }
        else if (items.get(position).getPrice() < 10.f) {
            holder.rightText.setText(String.format("%.3f", items.get(position).getPrice()) + " $");
        }
        else {
            holder.rightText.setText(String.format("%.2f", items.get(position).getPrice()) + " $");
        }
        holder.image.setImageResource(items.get(position).getImage());
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
