package com.example.mviredux.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.example.mviredux.R
import com.example.mviredux.databinding.FragmentCartBinding
import com.example.mviredux.model.ui.CartFragmentUiState
import com.example.mviredux.ui.adapter.controller.CartFragmentEpoxyController
import com.example.mviredux.viewModel.CartFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@AndroidEntryPoint
class CartFragment : Fragment(R.layout.fragment_cart) {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CartFragmentViewModel by viewModels()
    private val epoxyController: CartFragmentEpoxyController by lazy {
        CartFragmentEpoxyController(
            ::onEmptyCardClick,
            ::onFavoriteClick,
            ::onDeleteClicked
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCartBinding.bind(view)

        binding.epoxyRecyclerView.setController(epoxyController)

        viewModel.uiProductListReducer.reduce(viewModel.store).map { uiProducts ->
            uiProducts.filter { it.isInCart }
        }.distinctUntilChanged().asLiveData().observe(viewLifecycleOwner) { inCartUiProduct ->
            val viewState = if (inCartUiProduct.isEmpty()) {
                CartFragmentUiState.Empty
            } else {
                CartFragmentUiState.NonEmpty(inCartUiProduct)
            }
            epoxyController.setData(viewState)
        }

    }

    private fun onEmptyCardClick(view: View) {

    }

    private fun onFavoriteClick() {

    }

    private fun onDeleteClicked() {

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}