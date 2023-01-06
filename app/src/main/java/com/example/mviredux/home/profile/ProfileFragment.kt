package com.example.mviredux.home.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.example.mviredux.R
import com.example.mviredux.databinding.FragmentProfileBinding
import com.example.mviredux.viewModel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by viewModels()

    @Inject
    lateinit var profileItemGenerator: UserProfileItemGenerator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)

        val profileUiActions = ProfileUiActions(viewModel)
        val epoxyController = ProfileEpoxyController(profileItemGenerator, profileUiActions)

        binding.epoxyRecyclerView.setController(epoxyController)

        viewModel.store.stateFlow.map {
            it.authState
        }.distinctUntilChanged().asLiveData().observe(viewLifecycleOwner) { authState ->
            epoxyController.setData(authState)
            binding.headerTextView.text = authState?.getGreetingMessage()
            binding.infoTextView.text = authState?.getEmail()
        }

        viewModel.intentFlow.filterNotNull().asLiveData().observe(viewLifecycleOwner) { intent ->
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}