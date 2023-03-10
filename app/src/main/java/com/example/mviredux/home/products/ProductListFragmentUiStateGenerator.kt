package com.example.mviredux.home.products

import com.example.mviredux.model.ui.UiFilter
import com.example.mviredux.model.ui.UiProduct
import com.example.mviredux.redux.ApplicationState
import javax.inject.Inject

class ProductListFragmentUiStateGenerator @Inject constructor() {

    fun generate(
        listOfUiProducts: List<UiProduct>,
        productFilterInfo: ApplicationState.ProductFilterInfo
    ): ProductListFragmentUiState {
        if (listOfUiProducts.isEmpty()) {
            return ProductListFragmentUiState.Loading
        }

        val uiFilter = productFilterInfo.filters.map {
            UiFilter(
                filter = it,
                isSelected = productFilterInfo.selectedFilter?.equals(it) == true
            )
        }.toSet()

        val filteredProduct = if (productFilterInfo.selectedFilter == null) {
            listOfUiProducts
        } else {
            listOfUiProducts.filter { it.product.category == productFilterInfo.selectedFilter.value }
        }

        return ProductListFragmentUiState.Success(uiFilter, filteredProduct)
    }
}