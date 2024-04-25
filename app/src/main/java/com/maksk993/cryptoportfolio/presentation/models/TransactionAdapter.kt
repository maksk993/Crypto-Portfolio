package com.maksk993.cryptoportfolio.presentation.models

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maksk993.cryptoportfolio.R
import com.maksk993.cryptoportfolio.domain.models.Transaction
import com.maksk993.cryptoportfolio.domain.models.TransactionType

class TransactionAdapter(val context: Context, private var items : List<Transaction>) : RecyclerView.Adapter<AssetViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssetViewHolder {
        return AssetViewHolder(LayoutInflater.from(context).inflate(R.layout.asset_item, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: AssetViewHolder, position: Int) {
        holder.assetName.text = items[position].symbol
        holder.assetImage.setImageResource(
            if (items[position].type == TransactionType.BUY) R.drawable.ic_arrow_upward
            else R.drawable.ic_arrow_downward
        )
        val amount = items[position].amount
        holder.assetPrice.text = amount.toString()
    }
}