package com.maksk993.cryptoportfolio.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.maksk993.cryptoportfolio.R
import com.maksk993.cryptoportfolio.databinding.FragmentIndicateQuantityBinding
import com.maksk993.cryptoportfolio.presentation.models.FindFragmentById
import com.maksk993.cryptoportfolio.presentation.viewmodel.MainViewModel

class IndicateQuantityFragment : Fragment() {
    private lateinit var binding: FragmentIndicateQuantityBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIndicateQuantityBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        viewModel.addedAsset.observe(viewLifecycleOwner){
            binding.tvAssetName.text = it.symbol
            binding.editTextPrice.setText(it.price.toString())
        }

        binding.btnBackIq.setOnClickListener(){
            viewModel.openFragment(FindFragmentById.ADD_ASSET)
        }

        binding.btnAdd.setOnClickListener(){
            try {
                viewModel.addAssetToPortfolio(binding.editTextAmount.text.toString())
                viewModel.openFragment(FindFragmentById.PORTFOLIO)
            }
            catch (_: Exception){ }
        }

        viewModel.shouldNewFragmentBeOpened().observe(viewLifecycleOwner){
            val fragment = FindFragmentById.getFragment(it)
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.nav_host_fragment, fragment)
            transaction.commit()
        }

        return binding.root
    }
}