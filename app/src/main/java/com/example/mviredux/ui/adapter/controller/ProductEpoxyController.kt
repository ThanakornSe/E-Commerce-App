package com.example.mviredux.ui.adapter.controller

import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.TypedEpoxyController
import com.example.mviredux.ui.adapter.model.ProductEpoxyModel
import com.example.mviredux.ui.adapter.model.ProductFilterEpoxyModel
import com.example.mviredux.model.domain.Filter
import com.example.mviredux.model.ui.ProductsListFragmentUiState
import com.example.mviredux.model.ui.UiProduct
import java.util.UUID

class ProductEpoxyController(
    private val onFavoriteIconClicked: (Int) -> Unit,
    private val onAddToCartClicked: (Int) -> Unit,
    private val onProductClicked: (Int) -> Unit,
    private val onFilterSelected: (Filter) -> Unit
) : TypedEpoxyController<ProductsListFragmentUiState>() {

    override fun buildModels(data: ProductsListFragmentUiState?) {

        when (data) {
            is ProductsListFragmentUiState.Success -> {
                val uiFilterModels = data.filters.map { uiFilter ->
                    ProductFilterEpoxyModel(
                        uiFilter = uiFilter,
                        onFilterSelected = onFilterSelected
                    ).id(uiFilter.filter.value)
                }
                CarouselModel_().models(uiFilterModels).id("filters").addTo(this)

                data.products.forEach { uiProduct ->
                    ProductEpoxyModel(
                        uiProduct,
                        onFavoriteIconClicked,
                        onAddToCartClicked,
                        onProductClicked
                    ).id(uiProduct.product.id).addTo(this)
                }
            }
            is ProductsListFragmentUiState.Loading -> {
                repeat(7) {
                    val epoxyId = UUID.randomUUID().toString()
                    ProductEpoxyModel(
                        uiProduct = null,
                        onFavoriteIconClicked,
                        onAddToCartClicked,
                        onProductClicked
                    ).id(epoxyId).addTo(this)
                }
            }
            else -> {
                throw RuntimeException("Unhandled branch! $data")
            }
        }
    }


}