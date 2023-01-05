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
import com.example.mviredux.redux.reducer.UserProfileItemGenerator
import com.example.mviredux.ui.adapter.controller.ProfileEpoxyController
import com.example.mviredux.viewModel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by viewModels()

    @Inject
    lateinit var profileItemGenerator: UserProfileItemGenerator

    private val epoxyController:ProfileEpoxyController by lazy {
        ProfileEpoxyController(profileItemGenerator,
        ::onSignIn,
        ::onProfileItemSelected)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)

        binding.epoxyRecyclerView.setController(epoxyController)

        viewModel.store.stateFlow.map {
            it.user
        }.distinctUntilChanged().asLiveData().observe(viewLifecycleOwner) { user ->
            epoxyController.setData(user)
            binding.headerTextView.text = user?.greetingMessage ?: "Sign in"
            binding.infoTextView.text = user?.email
        }
    }

    private fun onSignIn(username: String, password: String) {
        viewModel.login(username, password)
    }

    private fun onProfileItemSelected(id: Int) {
        when (id) {
            R.drawable.ic_round_phone_24 -> {
                // call intent
            }
            R.drawable.ic_round_location_24 -> {
                // location intent
            }
            R.drawable.ic_round_logout_24 -> {
                viewModel.logout()
            }
            else -> {
                Log.i("SELECTION", "Unknown ID -> $id")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}