package com.maksk993.cryptoportfolio.presentation.screens.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.maksk993.cryptoportfolio.R
import com.maksk993.cryptoportfolio.databinding.FragmentPortfolioBinding
import com.maksk993.cryptoportfolio.presentation.models.recyclerview.AssetAdapter
import com.maksk993.cryptoportfolio.domain.models.Asset
import com.maksk993.cryptoportfolio.presentation.models.FindFragmentById
import com.maksk993.cryptoportfolio.presentation.screens.main.MainViewModel
import kotlin.math.floor


class PortfolioFragment : Fragment() {
    private lateinit var binding: FragmentPortfolioBinding
    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var adapter : AssetAdapter
    private val items : MutableList<Asset> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPortfolioBinding.inflate(inflater, container, false)

        initButtons()
        initObservers()

        return binding.root
    }

    private fun updatePortfolioView(asset : Asset){
        if (items.isNotEmpty()){
            for (i in items.indices){
                if (items[i].symbol == asset.symbol) {
                    val amountUsd = asset.amount * asset.price
                    if (amountUsd != items[i].price) {
                        items[i] = Asset(asset.symbol, amountUsd, asset.amount, image = R.drawable.ic_money)
                        adapter.notifyItemChanged(i)
                    }
                    return
                }
            }
        }
        items.add(Asset(asset.symbol, asset.amount * asset.price, asset.amount, image = R.drawable.ic_money))
        items.sortWith {
            a: Asset, b: Asset ->
            a.symbol.compareTo(b.symbol)
        }
        adapter.notifyDataSetChanged()
    }

    private fun removeAssetAndUpdateView(asset : Asset){
        for (i in items.indices){
            if (items[i].symbol == asset.symbol) {
                items.removeAt(i)
                adapter.notifyItemRemoved(i)
                return
            }
        }
    }

    private fun initObservers(){
        viewModel.assetsInPortfolio.observe(viewLifecycleOwner){
            if (it.size > 0) {
                binding.tvNoAssets.visibility = View.GONE
                initRecyclerView()
            }
            else binding.tvNoAssets.visibility = View.VISIBLE

            for (i in it){
                i?.let { updatePortfolioView(i) }
            }
        }

        viewModel.actualPrices.observe(viewLifecycleOwner){
            for (i in it) {
                val asset = viewModel.assetsInPortfolio.value?.find { item -> item?.symbol == i.key}
                asset?.let { updatePortfolioView(asset) }
            }
        }

        viewModel.removedAsset.observe(viewLifecycleOwner){
            removeAssetAndUpdateView(it)
        }

        viewModel.currentBalance.observe(viewLifecycleOwner){
            val balance = floor(it * 100.0) / 100.0
            binding.tvBalance.text = "$balance $"
        }
    }

    private fun initRecyclerView(){
        binding.rvPortfolio.setLayoutManager(LinearLayoutManager(requireContext()))
        adapter = AssetAdapter(requireContext(), items)
        adapter.setOnItemClickListener { position ->
            viewModel.setFocusedAsset(asset = items[position])
            viewModel.openFragment(FindFragmentById.ASSET_MANAGEMENT)
        }
        binding.rvPortfolio.adapter = adapter
    }

    private fun initButtons(){
        binding.btnAddAsset.setOnClickListener{
            viewModel.openFragment(FindFragmentById.ADD_ASSET)
        }
    }
}