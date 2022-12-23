package com.example.mviredux.redux

import com.androidfactory.fakestore.model.domain.Product

data class ApplicationState(
    val products: List<Product> = emptyList()
)
