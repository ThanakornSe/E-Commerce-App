package com.example.mviredux.redux

import com.androidfactory.fakestore.model.domain.Product
import com.example.mviredux.model.domain.Filter
import com.example.mviredux.model.domain.user.User

data class ApplicationState(
    val products: List<Product> = emptyList(),
    val favoriteProductIds: Set<Int> = emptySet(),
    val inCartProductIds: Set<Int> = emptySet(),
    val expandedProductIds: Set<Int> = emptySet(),
    val productFilterInfo: ProductFilterInfo = ProductFilterInfo(),
    val cartQuantitiesMap:Map<Int, Int> = emptyMap(), //productID -> Quantity
    val user: User? = null
) {
    data class ProductFilterInfo(
        val filters: Set<Filter> = emptySet(),
        val selectedFilter: Filter? = null
    )
}
//all of state of the app like in cart product change will store here