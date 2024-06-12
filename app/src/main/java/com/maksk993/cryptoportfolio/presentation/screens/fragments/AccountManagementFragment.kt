package com.maksk993.cryptoportfolio.presentation.screens.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.maksk993.cryptoportfolio.databinding.FragmentAccountManagementBinding
import com.maksk993.cryptoportfolio.domain.models.Account
import com.maksk993.cryptoportfolio.presentation.models.recyclerview.AccountAdapter
import com.maksk993.cryptoportfolio.presentation.screens.main.MainViewModel


class AccountManagementFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentAccountManagementBinding

    private lateinit var adapter : AccountAdapter
    private val items : MutableList<Account> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountManagementBinding.inflate(inflater, container, false)

        initRecyclerView()
        initObservers()
        initButtons()

        return binding.root
    }

    private fun initObservers() {
        viewModel.accounts.observe(viewLifecycleOwner){
            for (i in it){
                i?.let { addAccount(i) }
            }
        }
    }


    private fun initRecyclerView(){
        binding.rvAccounts.layoutManager = LinearLayoutManager(requireContext())
        adapter = AccountAdapter(requireContext(), items)
        adapter.setOnItemClickListener(object : AccountAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                viewModel.setCurrentAccount(items[position])
            }
        })
        binding.rvAccounts.adapter = adapter

        addMainPortfolio()
    }

    private fun addMainPortfolio() {
        val mainPortfolio = Account("Main Portfolio")
        addAccount(mainPortfolio)
        viewModel.addNewAccount(mainPortfolio)
    }

    private fun addAccount(account: Account){
        if (!items.contains(account)) {
            items.add(account)
            adapter.notifyItemInserted(items.size - 1)
        }
    }

    private fun initButtons() {
        binding.btnAddAccount.setOnClickListener{
            val alert = AlertDialog.Builder(requireContext())
            val input = EditText(requireContext())
            alert.setTitle("Creation of new portfolio")
            alert.setMessage("Enter the name of your new portfolio")
            alert.setView(input)
            alert.setPositiveButton("OK"){ _, _ ->
                if (!viewModel.addNewAccount(Account(input.text.toString())))
                    Toast.makeText(context, "Account with the same name already exists!", Toast.LENGTH_SHORT).show()
            }
            alert.setNegativeButton("Cancel"){ _, _ -> }
            alert.show()
        }
    }
}