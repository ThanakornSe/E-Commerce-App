package com.example.mviredux.adapter.controller

import com.airbnb.epoxy.TypedEpoxyController
import com.androidfactory.fakestore.model.domain.Product
import com.example.mviredux.adapter.model.ProductEpoxyModel

class ProductEpoxyController : TypedEpoxyController<List<Product>>() {

    override fun buildModels(data: List<Product>?) {
        if (data == null || data.isEmpty()) {
            // todo loading state
            return
        }

        data.forEach { product ->
            ProductEpoxyModel(product).id(product.id).addTo(this)
        }
    }
}