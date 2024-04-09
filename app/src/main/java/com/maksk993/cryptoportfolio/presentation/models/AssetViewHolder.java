package com.maksk993.cryptoportfolio.presentation.models;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maksk993.cryptoportfolio.R;

public class AssetViewHolder extends RecyclerView.ViewHolder {
    TextView assetName;
    TextView assetPrice;
    ImageView assetImage;

    public AssetViewHolder(@NonNull View itemView) {
        super(itemView);
        assetImage = itemView.findViewById(R.id.img_asset_image);
        assetName = itemView.findViewById(R.id.tv_asset_name);
        assetPrice = itemView.findViewById(R.id.tv_asset_price);
    }
}
