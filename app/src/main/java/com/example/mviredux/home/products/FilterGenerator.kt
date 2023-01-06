package com.example.mviredux.home.products

import com.androidfactory.fakestore.model.domain.Product
import com.example.mviredux.model.domain.Filter
import javax.inject.Inject

class FilterGenerator @Inject constructor() {

    //todo test me
    fun generateFrom(productList:List<Product>):Set<Filter> {
        return productList.groupBy { it.category }.map {
            Filter(value = it.key, displayText = "${it.key} (${it.value.size})")
        }.toSet()
    }
}