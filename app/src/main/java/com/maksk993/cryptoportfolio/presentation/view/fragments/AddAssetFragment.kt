package com.maksk993.cryptoportfolio.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.maksk993.cryptoportfolio.R
import com.maksk993.cryptoportfolio.databinding.FragmentAddAssetBinding
import com.maksk993.cryptoportfolio.presentation.models.AssetAdapter
import com.maksk993.cryptoportfolio.domain.models.AssetItem
import com.maksk993.cryptoportfolio.presentation.models.FindFragmentById
import com.maksk993.cryptoportfolio.presentation.viewmodel.MainViewModel


class AddAssetFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentAddAssetBinding

    private lateinit var adapter : AssetAdapter
    private val items : MutableList<AssetItem> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddAssetBinding.inflate(inflater, container, false)

        initRecyclerView()
        initButtons()
        initObservers()

        return binding.root
    }

    private fun initObservers(){
        viewModel.actualPrices.observe(viewLifecycleOwner){
            for (i in it){
                updatePrices(i.key, i.value)
            }
        }

        viewModel.updatingAsset.observe(viewLifecycleOwner){
            updatePrices(it.symbol, it.price)
        }
    }

    private fun updatePrices(symbol : String, price : Float){
        if (items.isNotEmpty()){
            for (i in 0 until items.size){
                if (items[i].symbol == symbol) {
                    if (price != items[i].price) {
                        items[i] = AssetItem(symbol, price, R.drawable.ic_money)
                        adapter.notifyItemChanged(i)
                    }
                    return
                }
            }
        }
        items.add(AssetItem(symbol, price, R.drawable.ic_money))
        items.sortWith {
            a: AssetItem, b: AssetItem ->
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