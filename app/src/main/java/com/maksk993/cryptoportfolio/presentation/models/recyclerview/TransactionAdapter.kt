package com.maksk993.cryptoportfolio.presentation.models.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maksk993.cryptoportfolio.R
import com.maksk993.cryptoportfolio.domain.models.Transaction
import com.maksk993.cryptoportfolio.domain.models.TransactionType

class TransactionAdapter(val context: Context, private var items : List<Transaction>) : RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(R.layout.recycler_view_item, parent, false)
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.leftName.text = items[position].symbol
        holder.image.setImageResource(
            if (items[position].type == TransactionType.BUY) R.drawable.ic_arrow_upward
            else R.drawable.ic_arrow_downward
        )
        val amount = items[position].amount
        holder.rightText.text = amount.toString()
    }
}