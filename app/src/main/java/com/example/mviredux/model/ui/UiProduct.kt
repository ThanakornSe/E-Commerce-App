package com.example.mviredux.model.ui

import com.androidfactory.fakestore.model.domain.Product

data class UiProduct(
    val product: Product,
    val isFavorite:Boolean = false
)
