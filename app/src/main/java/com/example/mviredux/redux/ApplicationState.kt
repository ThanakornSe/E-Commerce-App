package com.example.mviredux.redux

import com.androidfactory.fakestore.model.domain.Product

data class ApplicationState(
    val products: List<Product> = emptyList(),
    val favoriteProductIds: Set<Int> = emptySet(),
    val inCartProductIds: Set<Int> = emptySet()
)
//all of state of the app like in cart product change will store here