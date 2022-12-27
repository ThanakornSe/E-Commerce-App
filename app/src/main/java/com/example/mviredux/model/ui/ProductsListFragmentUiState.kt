package com.example.mviredux.model.ui

import com.example.mviredux.model.domain.Filter

sealed interface ProductsListFragmentUiState {
    data class Success(
        val filters:Set<UiFilter>,
        val products:List<UiProduct>
    ):ProductsListFragmentUiState

    object Loading:ProductsListFragmentUiState
}
