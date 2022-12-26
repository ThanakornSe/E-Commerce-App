package com.example.mviredux.adapter.controller

import com.airbnb.epoxy.TypedEpoxyController
import com.example.mviredux.adapter.model.ProductEpoxyModel
import com.example.mviredux.model.ui.UiProduct

class ProductEpoxyController : TypedEpoxyController<List<UiProduct>>() {

    override fun buildModels(data: List<UiProduct>?) {
        if (data.isNullOrEmpty()) {
            repeat(7) {
                val epoxyId = it + 1
                ProductEpoxyModel(uiProduct = null).id(epoxyId).addTo(this)
            }
        }else {
            data.forEach { uiProduct ->
                ProductEpoxyModel(uiProduct).id(uiProduct.product.id).addTo(this)
            }
        }
    }
}