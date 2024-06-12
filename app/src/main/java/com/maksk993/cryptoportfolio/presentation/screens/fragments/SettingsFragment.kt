package com.maksk993.cryptoportfolio.presentation.screens.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.maksk993.cryptoportfolio.databinding.FragmentSettingsBinding
import com.maksk993.cryptoportfolio.presentation.screens.main.MainViewModel

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        binding.switchTheme.apply {
            if (isNightTheme()) setChecked(true)
            setOnClickListener{
                viewModel.switchTheme()
            }
        }

        return binding.root
    }

    private fun isNightTheme(): Boolean = viewModel.nightMode.value ?: false
}