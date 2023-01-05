package com.example.mviredux.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.example.mviredux.R
import com.example.mviredux.databinding.FragmentProfileBinding
import com.example.mviredux.viewModel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)

        binding.btnLogin.setOnClickListener {
            viewModel.login("mor_2314", "83r5^_")
        }

        viewModel.store.stateFlow.map {
            it.user
        }.distinctUntilChanged().asLiveData()
            .observe(viewLifecycleOwner) {
                binding.headerTextView.text = it?.greetingMessage ?: "Sign in"
                Log.i("USER", it.toString())
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}