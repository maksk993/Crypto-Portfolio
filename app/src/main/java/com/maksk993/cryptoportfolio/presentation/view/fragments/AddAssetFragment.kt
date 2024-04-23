package com.maksk993.cryptoportfolio.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.maksk993.cryptoportfolio.R
import com.maksk993.cryptoportfolio.databinding.FragmentAddAssetBinding
import com.maksk993.cryptoportfolio.presentation.models.AssetAdapter
import com.maksk993.cryptoportfolio.domain.models.AssetItem
import com.maksk993.cryptoportfolio.presentation.models.FindFragmentById
import com.maksk993.cryptoportfolio.presentation.viewmodel.MainViewModel


class AddAssetFragment : Fragment() {
    private lateinit var viewModel : MainViewModel
    private lateinit var binding: FragmentAddAssetBinding
    private lateinit var adapter : AssetAdapter
    private val items : MutableList<AssetItem> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddAssetBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        binding.recycler.setLayoutManager(LinearLayoutManager(requireContext()))
        adapter = AssetAdapter(requireContext(), items)
        adapter.setOnItemClickListener { position ->
            viewModel.setFocusedAsset(items[position])
            viewModel.openFragment(FindFragmentById.INDICATE_QUANTITY)
        }
        binding.recycler.adapter = adapter

        binding.btnBack.setOnClickListener {
            viewModel.openFragment(FindFragmentById.PORTFOLIO)
        }

        viewModel.actualPrices.observe(viewLifecycleOwner){
            for (i in it){
                updatePrices(i.key, i.value)
            }
        }

        viewModel.shouldNewFragmentBeOpened().observe(viewLifecycleOwner){
            val fragment = FindFragmentById.getFragment(it)
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.nav_host_fragment, fragment)
            transaction.commit()
        }
        viewModel.getUpdatingAsset().observe(viewLifecycleOwner){
            updatePrices(it.symbol, it.price)
        }

        return binding.root
    }

    private fun updatePrices(symbol : String, price : Float){
        if (items.isNotEmpty()){
            for (i in 0 until items.size){
                if (items[i].symbol == symbol) {
                    if (price != items[i].price) {
                        items[i] =
                            AssetItem(
                                symbol,
                                price,
                                R.drawable.ic_money
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
                price,
                R.drawable.ic_money
            )
        )
        items.sortWith {
            a: AssetItem, b: AssetItem ->
            a.symbol.compareTo(b.symbol)
        }
        adapter.notifyDataSetChanged()
    }
}