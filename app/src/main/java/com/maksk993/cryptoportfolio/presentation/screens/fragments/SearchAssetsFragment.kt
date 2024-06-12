package com.maksk993.cryptoportfolio.presentation.screens.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.maksk993.cryptoportfolio.R
import com.maksk993.cryptoportfolio.databinding.FragmentSearchAssetsBinding
import com.maksk993.cryptoportfolio.presentation.models.recyclerview.AssetAdapter
import com.maksk993.cryptoportfolio.domain.models.Asset
import com.maksk993.cryptoportfolio.presentation.models.FindFragmentById
import com.maksk993.cryptoportfolio.presentation.screens.main.MainViewModel


class SearchAssetsFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentSearchAssetsBinding

    private lateinit var adapter : AssetAdapter
    private val items : MutableList<Asset> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchAssetsBinding.inflate(inflater, container, false)

        initRecyclerView()
        initSearchView()
        initButtons()
        initObservers()

        return binding.root
    }

    private fun initSearchView() {
        binding.svSearchAssets.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                var list = listOf<Asset>()
                query?.let { list = viewModel.getAssetsBySearch(query) }
                items.clear()
                binding.recycler.adapter?.notifyDataSetChanged()
                for (i in list) {
                    updatePrices(i.symbol, i.price)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = onQueryTextSubmit(newText)
        })
    }

    private fun initObservers(){
        viewModel.actualPrices.observe(viewLifecycleOwner){
            for (i in it){
                updatePrices(i.key, i.value)
            }
        }
    }

    private fun updatePrices(symbol : String, price : Float){
        if (items.isNotEmpty()){
            for (i in 0 until items.size){
                if (items[i].symbol == symbol) {
                    if (price != items[i].price) {
                        items[i] = Asset(symbol, price, image = R.drawable.ic_money)
                        adapter.notifyItemChanged(i)
                    }
                    return
                }
            }
        }
        items.add(Asset(symbol, price, image = R.drawable.ic_money))
        items.sortWith {
                a: Asset, b: Asset ->
            a.symbol.compareTo(b.symbol)
        }
        adapter.notifyDataSetChanged()
    }

    private fun initRecyclerView(){
        binding.recycler.setLayoutManager(LinearLayoutManager(requireContext()))
        adapter = AssetAdapter(requireContext(), items)
        adapter.setOnItemClickListener { position ->
            viewModel.setFocusedAsset(items[position])
            viewModel.openFragment(FindFragmentById.INDICATE_QUANTITY)
        }
        binding.recycler.adapter = adapter
    }

    private fun initButtons(){
        binding.btnBack.setOnClickListener {
            viewModel.openFragment(FindFragmentById.PORTFOLIO)
        }
    }
}