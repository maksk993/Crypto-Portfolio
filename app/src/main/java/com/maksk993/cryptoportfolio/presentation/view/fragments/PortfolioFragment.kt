package com.maksk993.cryptoportfolio.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.maksk993.cryptoportfolio.R
import com.maksk993.cryptoportfolio.databinding.FragmentPortfolioBinding
import com.maksk993.cryptoportfolio.presentation.models.AssetAdapter
import com.maksk993.cryptoportfolio.domain.models.AssetItem
import com.maksk993.cryptoportfolio.domain.models.PortfolioAssetItem
import com.maksk993.cryptoportfolio.presentation.models.FindFragmentById
import com.maksk993.cryptoportfolio.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.floor


@AndroidEntryPoint
class PortfolioFragment : Fragment() {
    private lateinit var binding: FragmentPortfolioBinding
    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var adapter : AssetAdapter
    private val items : MutableList<AssetItem> = ArrayList()

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

    private fun initObservers(){
        viewModel.assetsInPortfolio.observe(viewLifecycleOwner){
            if (it.size > 0) {
                binding.tvNoAssets.visibility = View.GONE
                initRecyclerView()
            }
            else binding.tvNoAssets.visibility = View.VISIBLE

            for (i in it){
                updatePortfolioView(i!!)
            }
        }

        viewModel.removedAsset.observe(viewLifecycleOwner){
            removeAssetAndUpdateView(it)
        }

        viewModel.nextFragment.observe(viewLifecycleOwner){
            val fragment = FindFragmentById.getFragment(it)
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.nav_host_fragment, fragment)
            transaction.commit()
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
            viewModel.setFocusedAsset(assetItem = items[position], amount = viewModel.getAmountOf(items[position].symbol))
            viewModel.openFragment(FindFragmentById.ASSET_MANAGEMENT)
        }
        binding.rvPortfolio.adapter = adapter
    }

    private fun initButtons(){
        binding.btnAddAsset.setOnClickListener(){
            viewModel.openFragment(FindFragmentById.ADD_ASSET)
        }
    }
}