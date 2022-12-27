package com.example.mviredux.model.ui

import com.example.mviredux.model.domain.Filter

data class ProductsListFragmentUiState(
    val filters: Set<UiFilter>,
    val products: List<UiProduct>
)
