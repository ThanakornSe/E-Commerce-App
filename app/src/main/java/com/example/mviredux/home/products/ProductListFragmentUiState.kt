package com.example.mviredux.home.products

import com.example.mviredux.model.ui.UiFilter
import com.example.mviredux.model.ui.UiProduct

sealed interface ProductListFragmentUiState {
    data class Success(
        val filters:Set<UiFilter>,
        val products:List<UiProduct>
    ): ProductListFragmentUiState

    object Loading: ProductListFragmentUiState
}
