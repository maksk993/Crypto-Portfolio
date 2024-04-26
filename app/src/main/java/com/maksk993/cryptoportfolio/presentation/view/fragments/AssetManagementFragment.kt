package com.maksk993.cryptoportfolio.presentation.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.maksk993.cryptoportfolio.R
import com.maksk993.cryptoportfolio.databinding.FragmentAssetManagementBinding
import com.maksk993.cryptoportfolio.domain.models.TransactionType
import com.maksk993.cryptoportfolio.presentation.models.FindFragmentById
import com.maksk993.cryptoportfolio.presentation.viewmodel.MainViewModel


class AssetManagementFragment : Fragment() {
    private lateinit var binding : FragmentAssetManagementBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAssetManagementBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        initButtons()
        initObservers()

        return binding.root
    }

    private fun initObservers(){
        viewModel.focusedAsset.observe(viewLifecycleOwner){
            binding.editTextAmount.setText("")
            binding.tvAmount.text = "${it.amount}"
            binding.tvAsset.text = " ${it.symbol} = "

            if (it.price < 0F) {
                binding.editTextPrice.setText("?")
                binding.tvUsdAmount.text = "? "
            }
            else {
                binding.editTextPrice.setText(it.price.toString())
                binding.tvUsdAmount.text = "${it.price * it.amount} "
            }
        }

        viewModel.nextFragment.observe(viewLifecycleOwner){
            val fragment = FindFragmentById.getFragment(it)
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.nav_host_fragment, fragment)
            transaction.commit()
        }
    }

    private fun initButtons(){
        binding.btnPlus.setOnClickListener{
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

        binding.btnMinus.setOnClickListener{
            try {
                if (binding.editTextPrice.text.toString() == "?") {
                    Toast.makeText(context, "Try it later", Toast.LENGTH_SHORT).show()
                }
                else if (binding.editTextAmount.text.toString().toFloat() > binding.tvAmount.text.toString().toFloat()) {
                    Toast.makeText(context, "You can't sell more than you have", Toast.LENGTH_SHORT).show()
                }
                else {
                    val amount = -binding.editTextAmount.text.toString().toFloat()
                    viewModel.addAssetToPortfolio(amount)
                    viewModel.saveTransaction(binding.editTextPrice.text.toString().toFloat(), amount, TransactionType.SELL)
                    viewModel.openFragment(FindFragmentById.PORTFOLIO)
                    Toast.makeText(context, "Asset was sold successfully", Toast.LENGTH_SHORT).show()
                }
            }
            catch (_: Exception){
                Toast.makeText(context, "Enter the amount", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnBack.setOnClickListener{
            viewModel.openFragment(FindFragmentById.PORTFOLIO)
        }

        binding.btnRemoveAll.setOnClickListener{
            if (binding.editTextPrice.text.toString() == "?") {
                Toast.makeText(context, "Try it later", Toast.LENGTH_SHORT).show()
            }
            else {
                viewModel.saveTransaction(binding.editTextPrice.text.toString().toFloat(), type = TransactionType.SELL)
                viewModel.removeAssetFromPortfolio()
                viewModel.openFragment(FindFragmentById.PORTFOLIO)
                Toast.makeText(context, "Asset was sold successfully", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnGoToHistory.setOnClickListener{

        }
    }
}