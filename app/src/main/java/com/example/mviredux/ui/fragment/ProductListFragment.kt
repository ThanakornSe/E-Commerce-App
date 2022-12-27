package com.example.mviredux.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.mviredux.R
import com.example.mviredux.adapter.controller.ProductEpoxyController
import com.example.mviredux.databinding.FragmentProductListBinding
import com.example.mviredux.model.domain.Filter
import com.example.mviredux.model.ui.ProductsListFragmentUiState
import com.example.mviredux.model.ui.UiFilter
import com.example.mviredux.model.ui.UiProduct
import com.example.mviredux.viewModel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

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
            viewModel.store.stateFlow.map { it.products },
            viewModel.store.stateFlow.map { it.favoriteProductIds },
            viewModel.store.stateFlow.map { it.inCartProductIds },
            viewModel.store.stateFlow.map { it.expandedProductIds },
            viewModel.store.stateFlow.map { it.productFilterInfo }
        ) { listOfProducts, setOfFavoriteIds, setOfInCartIds, setOfExpandedIds, productFilterInfo ->

            if (listOfProducts.isEmpty()) {
                return@combine ProductsListFragmentUiState.Loading
            }

            val uiProduct = listOfProducts.map { product ->
                UiProduct(
                    product = product,
                    isFavorite = setOfFavoriteIds.contains(product.id),
                    isInCart = setOfInCartIds.contains(product.id),
                    isExpanded = setOfExpandedIds.contains(product.id)
                )
            }

            val uiFilter = productFilterInfo.filters.map {
                UiFilter(
                    filter = it,
                    isSelected = productFilterInfo.selectedFilter?.equals(it) == true
                )
            }.toSet()

            val filteredProduct = if (productFilterInfo.selectedFilter == null) {
                uiProduct
            } else {
                uiProduct.filter { it.product.category == productFilterInfo.selectedFilter.value }
            }

            return@combine ProductsListFragmentUiState.Success(uiFilter, filteredProduct)
        }.distinctUntilChanged().asLiveData().observe(viewLifecycleOwner) { uiState ->
            epoxyController.setData(uiState)
        }

        viewModel.fetchProducts()
    }

    private fun onFavoriteIconClicked(productId: Int) {
        viewModel.viewModelScope.launch {
            viewModel.store.update { currentState ->
                val currentFavIds = currentState.favoriteProductIds
                val newFavoriteIds: Set<Int> = if (currentFavIds.contains(productId)) {
                    currentFavIds.filter { it != productId }.toSet()
                    //this scope mean user deselected favorite
                } else {
                    //this scope mean user select favorite
                    currentFavIds + setOf(productId)
                }
                return@update currentState.copy(favoriteProductIds = newFavoriteIds)
            }
        }
    }

    private fun onAddToCartClicked(productId: Int) {
        viewModel.viewModelScope.launch {
            viewModel.store.update { currentState ->
                val currentInCartIds = currentState.inCartProductIds
                val newInCartIds: Set<Int> = if (currentInCartIds.contains(productId)) {
                    currentInCartIds.filter { it != productId }.toSet()
                    //this scope mean user deselected from Cart
                } else {
                    //this scope mean user select to InCart
                    currentInCartIds + setOf(productId)
                }
                return@update currentState.copy(inCartProductIds = newInCartIds)
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
                return@update currentState.copy(
                    productFilterInfo = currentState.productFilterInfo.copy(
                        selectedFilter = if (currentlySelectedFilter != filter) {
                            filter
                        } else {
                            null
                        }
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