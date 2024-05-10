package com.maksk993.cryptoportfolio.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.maksk993.cryptoportfolio.R
import com.maksk993.cryptoportfolio.databinding.FragmentIndicateQuantityBinding
import com.maksk993.cryptoportfolio.domain.models.TransactionType
import com.maksk993.cryptoportfolio.presentation.models.Filter
import com.maksk993.cryptoportfolio.presentation.models.FindFragmentById
import com.maksk993.cryptoportfolio.presentation.viewmodel.MainViewModel


class IndicateQuantityFragment : Fragment() {
    private lateinit var binding: FragmentIndicateQuantityBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIndicateQuantityBinding.inflate(inflater, container, false)

        initButtons()
        initObservers()

        return binding.root
    }

    private fun initObservers() {
        viewModel.focusedAsset.observe(viewLifecycleOwner){
            binding.tvAssetName.text = it.symbol
            binding.editTextAmount.setText("")
            if (it.price < 0F) binding.editTextPrice.setText("?")
            else binding.editTextPrice.setText(it.price.toString())
        }

        viewModel.nextFragment.observe(viewLifecycleOwner){
            val fragment = FindFragmentById.getFragment(it)
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.nav_host_fragment, fragment)
            transaction.commit()
        }
    }

    private fun initButtons() {
        binding.editTextAmount.filters = arrayOf(Filter.execute())
        binding.editTextPrice.filters = arrayOf(Filter.execute())

        binding.btnBackIq.setOnClickListener{
            viewModel.openFragment(FindFragmentById.ADD_ASSET)
        }

        binding.btnAdd.setOnClickListener{
            try {
                if (binding.editTextPrice.text.toString() == "?") {
                    Toast.makeText(context, "Try it later", Toast.LENGTH_SHORT).show()
                }
                else {
                    val amount = binding.editTextAmount.text.toString().toFloat()
                    viewModel.addAssetToPortfolio(amount)
                    viewModel.saveTransaction(binding.editTextPrice.text.toString().toFloat(), amount, TransactionType.BUY)
                    viewModel.openFragment(FindFragmentById.PORTFOLIO)
                    Toast.makeText(context, "Asset was added successfully", Toast.LENGTH_SHORT).show()
                }
            }
            catch (_: Exception){
                Toast.makeText(context, "Enter the amount", Toast.LENGTH_SHORT).show()
            }
        }
    }
}