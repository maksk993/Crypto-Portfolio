package com.maksk993.cryptoportfolio.presentation.models

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maksk993.cryptoportfolio.R
import com.maksk993.cryptoportfolio.domain.models.Account


class AccountAdapter(val context: Context, private var items : List<Account>): RecyclerView.Adapter<MyViewHolder>() {
    private lateinit var listener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(R.layout.recycler_view_item, parent, false)
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.leftName.text = items[position].name
        holder.itemView.setOnClickListener{
            listener.onItemClick(position)
        }
    }
}