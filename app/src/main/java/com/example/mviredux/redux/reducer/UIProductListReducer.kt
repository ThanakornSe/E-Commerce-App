package com.example.mviredux.redux.reducer

import com.example.mviredux.model.ui.UiProduct
import com.example.mviredux.redux.ApplicationState
import com.example.mviredux.redux.Store
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UIProductListReducer @Inject constructor() {

    fun reduce(store: Store<ApplicationState>): Flow<List<UiProduct>> {
        return  combine(
            store.stateFlow.map { it.products },
            store.stateFlow.map { it.favoriteProductIds },
            store.stateFlow.map { it.inCartProductIds },
            store.stateFlow.map { it.expandedProductIds }
        ) { listOfProducts, setOfFavoriteIds, setOfInCartIds, setOfExpandedIds ->

            if (listOfProducts.isEmpty()) {
                return@combine emptyList<UiProduct>()
            }

            val uiProduct = listOfProducts.map { product ->
                UiProduct(
                    product = product,
                    isFavorite = setOfFavoriteIds.contains(product.id),
                    isInCart = setOfInCartIds.contains(product.id),
                    isExpanded = setOfExpandedIds.contains(product.id)
                )
            }

            return@combine uiProduct
        }
    }
}