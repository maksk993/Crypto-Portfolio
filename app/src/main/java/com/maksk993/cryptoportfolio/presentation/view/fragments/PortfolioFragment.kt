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
import com.maksk993.cryptoportfolio.domain.models.PortfolioAssetItem
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
        adapter.setOnItemClickListener { position ->
            viewModel.setFocusedAsset(assetItem = items[position], amount = viewModel.getAmountOf(items[position].symbol))
            viewModel.openFragment(FindFragmentById.ASSET_MANAGEMENT)
        }
        binding.rvPortfolio.adapter = adapter

        viewModel.assetsInPortfolio.observe(viewLifecycleOwner){
            if (it.size > 0) binding.tvNoAssets.visibility = View.GONE
            else binding.tvNoAssets.visibility = View.VISIBLE
            for (i in it){
                updatePortfolioView(i!!)
            }
        }

        viewModel.removedAsset.observe(viewLifecycleOwner){
            if (!items.contains(it)) {
                removeAssetAndUpdateView(it)
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
        }

        return binding.root
    }

    private fun updatePortfolioView(asset : PortfolioAssetItem){
        if (items.isNotEmpty()){
            for (i in items.indices){
                if (items[i].symbol == asset.symbol) {
                    val amountUsd = asset.amount * asset.price
                    if (amountUsd != items[i].price) {
                        items[i] = AssetItem(asset.symbol, amountUsd, R.drawable.ic_money)
                        adapter.notifyItemChanged(i)
                    }
                    return
                }
            }
        }
        items.add(AssetItem(asset.symbol, asset.amount * asset.price, R.drawable.ic_money))
        items.sortWith {
            a: AssetItem, b: AssetItem ->
            a.symbol.compareTo(b.symbol)
        }
        adapter.notifyDataSetChanged()
    }

    private fun removeAssetAndUpdateView(asset : AssetItem){
        for (i in items.indices){
            if (items[i].symbol == asset.symbol) {
                items.removeAt(i)
                adapter.notifyItemRemoved(i)
                return
            }
        }
    }
}