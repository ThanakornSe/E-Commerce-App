package com.example.mviredux.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.mviredux.databinding.FragmentProductListBinding
import com.example.mviredux.model.domain.Filter
import com.example.mviredux.redux.reducer.ProductListFragmentUiStateGenerator
import com.example.mviredux.ui.adapter.controller.ProductEpoxyController
import com.example.mviredux.viewModel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ProductListFragment : Fragment() {
    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!

    private val epoxyController: ProductEpoxyController by lazy {
        ProductEpoxyController(
            ::onFavoriteIconClicked,
            ::onAddToCartClicked,
            ::onUiProductClicked,
            ::onFilterSelected
        )
    }

    private val viewModel: MainActivityViewModel by viewModels()

    @Inject
    lateinit var uiStateGenerator: ProductListFragmentUiStateGenerator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.epoxyRecyclerView.setController(epoxyController)

        combine(
            viewModel.uiProductListReducer.reduce(viewModel.store),
            viewModel.store.stateFlow.map { it.productFilterInfo }
        ) { listOfUiProducts, productFilterInfo ->

            uiStateGenerator.generate(listOfUiProducts,productFilterInfo)

        }.distinctUntilChanged().asLiveData().observe(viewLifecycleOwner) { uiState ->
            epoxyController.setData(uiState)
        }

        viewModel.fetchProducts()
    }

    private fun onFavoriteIconClicked(productId: Int) {
        viewModel.viewModelScope.launch {
            viewModel.store.update { currentState ->
                return@update viewModel.uiProductFavoriteUpdater.onProductFavoriteUpdate(
                    productId = productId, currentState = currentState
                )
            }
        }
    }

    private fun onAddToCartClicked(productId: Int) {
        viewModel.viewModelScope.launch {
            viewModel.store.update { currentState ->
                return@update viewModel.uiProductInCartUpdater.onProductInCartUpdate(
                    productId = productId, currentState = currentState
                )
            }
        }
    }

    private fun onUiProductClicked(productId: Int) {
        viewModel.viewModelScope.launch {
            viewModel.store.update { currentState ->
                val currentExpandedIds = currentState.expandedProductIds
                val newExpandedIds: Set<Int> = if (currentExpandedIds.contains(productId)) {
                    currentExpandedIds.filter { it != productId }.toSet()
                    //this scope mean user deselected from Cart
                } else {
                    //this scope mean user select to InCart
                    currentExpandedIds + setOf(productId)
                }
                return@update currentState.copy(expandedProductIds = newExpandedIds)
            }
        }
    }

    private fun onFilterSelected(filter: Filter) {
        viewModel.viewModelScope.launch {
            viewModel.store.update { currentState ->
                val currentlySelectedFilter = currentState.productFilterInfo.selectedFilter
                val newSelectedFilter = if (currentlySelectedFilter != filter) {
                    filter
                } else {
                    null
                }
                return@update currentState.copy(
                    productFilterInfo = currentState.productFilterInfo.copy(
                        selectedFilter = newSelectedFilter
                    )
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}