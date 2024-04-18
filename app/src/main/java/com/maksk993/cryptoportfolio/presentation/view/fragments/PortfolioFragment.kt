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

        viewModel.shouldNewFragmentBeOpened().observe(viewLifecycleOwner){
            val fragment = FindFragmentById.getFragment(it)
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.nav_host_fragment, fragment)
            transaction.commit()
        }

        binding.rvPortfolio.setLayoutManager(LinearLayoutManager(requireContext()))
        adapter = AssetAdapter(requireContext(), items)
        binding.rvPortfolio.adapter = adapter

        return binding.root
    }

    fun updatePortfolioView(symbol : String, price : Float){
        if (items.isNotEmpty()){
            for (i in 0 until items.size){
                if (items[i].symbol == symbol) {
                    if (price != items[i].price) {
                        items[i] =
                            AssetItem(
                                symbol,
                                price,
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
                price,
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