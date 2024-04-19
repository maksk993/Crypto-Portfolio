package com.maksk993.cryptoportfolio.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.maksk993.cryptoportfolio.R
import com.maksk993.cryptoportfolio.databinding.FragmentPortfolioBinding
import com.maksk993.cryptoportfolio.presentation.models.AssetAdapter
import com.maksk993.cryptoportfolio.domain.models.AssetItem
import com.maksk993.cryptoportfolio.presentation.models.FindFragmentById
import com.maksk993.cryptoportfolio.presentation.viewmodel.MainViewModel
import kotlin.math.floor


class PortfolioFragment : Fragment() {
    private lateinit var binding: FragmentPortfolioBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter : AssetAdapter
    private val items : MutableList<AssetItem> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPortfolioBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        binding.btnAddAsset.setOnClickListener(){
            viewModel.openFragment(FindFragmentById.ADD_ASSET)
        }

        binding.rvPortfolio.setLayoutManager(LinearLayoutManager(requireContext()))
        adapter = AssetAdapter(requireContext(), items)
        binding.rvPortfolio.adapter = adapter

        viewModel.assetsInPortfolio.observe(viewLifecycleOwner){
            for (i in it){
                val assetAmountUsd = i!!.amount * i.price
                updatePortfolioView(i.symbol, assetAmountUsd)
            }
        }
        viewModel.shouldNewFragmentBeOpened().observe(viewLifecycleOwner){
            val fragment = FindFragmentById.getFragment(it)
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.nav_host_fragment, fragment)
            transaction.commit()
        }

        viewModel.currentBalance.observe(viewLifecycleOwner){
            val balance = floor(it * 100.0) / 100.0
            binding.tvBalance.text = "$balance $"
            if (it != 0F) binding.tvNoAssets.visibility = View.GONE
        }

        return binding.root
    }

    private fun updatePortfolioView(symbol : String, amount : Float){
        if (items.isNotEmpty()){
            for (i in 0 until items.size){
                if (items[i].symbol == symbol) {
                    if (amount != items[i].price) {
                        items[i] =
                            AssetItem(
                                symbol,
                                amount,
                                R.drawable.ic_history
                            )
                        adapter.notifyItemChanged(i)
                    }
                    return
                }
            }
        }
        items.add(
            AssetItem(
                symbol,
                amount,
                R.drawable.ic_history
            )
        )
        items.sortWith {
            a: AssetItem, b: AssetItem ->
            a.symbol.compareTo(b.symbol)
        }
        adapter.notifyDataSetChanged()
    }
}