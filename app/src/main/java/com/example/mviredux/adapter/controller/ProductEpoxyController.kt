package com.example.mviredux.adapter.controller

import com.airbnb.epoxy.TypedEpoxyController
import com.example.mviredux.adapter.model.ProductEpoxyModel
import com.example.mviredux.model.ui.UiProduct

class ProductEpoxyController(
    private val onFavoriteIconClicked: (Int) -> Unit,
    private val onAddToCartClicked: (Int) -> Unit,
    private val onProductClicked: (Int) -> Unit
) : TypedEpoxyController<List<UiProduct>>() {

    override fun buildModels(data: List<UiProduct>?) {
        if (data.isNullOrEmpty()) {
            repeat(7) {
                val epoxyId = it + 1
                ProductEpoxyModel(
                    uiProduct = null,
                    onFavoriteIconClicked,
                    onAddToCartClicked,
                    onProductClicked
                ).id(epoxyId).addTo(this)
            }
        } else {
            data.forEach { uiProduct ->
                ProductEpoxyModel(
                    uiProduct,
                    onFavoriteIconClicked,
                    onAddToCartClicked,
                    onProductClicked
                ).id(uiProduct.product.id).addTo(this)
            }
        }
    }


}