package com.maksk993.cryptoportfolio.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.maksk993.cryptoportfolio.databinding.FragmentHistoryBinding
import com.maksk993.cryptoportfolio.domain.models.Transaction
import com.maksk993.cryptoportfolio.presentation.models.TransactionAdapter
import com.maksk993.cryptoportfolio.presentation.viewmodel.MainViewModel


class HistoryFragment : Fragment() {
    private val viewModel : MainViewModel by activityViewModels()
    private lateinit var binding : FragmentHistoryBinding

    private lateinit var adapter : TransactionAdapter
    private val items : MutableList<Transaction> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)

        initObservers()

        return binding.root
    }

    private fun initObservers(){
        viewModel.transactions.observe(viewLifecycleOwner){
            if (it.size > 0) {
                binding.tvNoHistory.visibility = View.GONE
                initRecyclerView()
            }
            else binding.tvNoHistory.visibility = View.VISIBLE

            for (i in it){
                updateHistoryView(i!!)
            }
        }
    }

    private fun updateHistoryView(transaction: Transaction){
        if (items.contains(transaction)) return
        items.add(0, transaction)
        adapter.notifyItemInserted(0)
        binding.rvHistory.smoothScrollToPosition(0)
    }

    private fun initRecyclerView(){
        binding.rvHistory.layoutManager = LinearLayoutManager(requireContext())
        adapter = TransactionAdapter(requireContext(), items)
        binding.rvHistory.adapter = adapter
    }
}