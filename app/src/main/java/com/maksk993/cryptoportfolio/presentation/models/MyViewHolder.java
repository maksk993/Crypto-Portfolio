package com.maksk993.cryptoportfolio.presentation.models;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maksk993.cryptoportfolio.R;

public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView leftName;
    TextView rightText;
    ImageView image;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.img_image);
        leftName = itemView.findViewById(R.id.tv_left_text);
        rightText = itemView.findViewById(R.id.tv_right_text);
    }
}
