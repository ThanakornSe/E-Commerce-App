package com.example.mviredux.redux.updater

import com.example.mviredux.redux.ApplicationState
import javax.inject.Inject

class UiProductFavoriteUpdater @Inject constructor() {

    fun onProductFavoriteUpdate(productId:Int, currentState:ApplicationState): ApplicationState {
        val currentFavIds = currentState.favoriteProductIds
        val newFavoriteIds: Set<Int> = if (currentFavIds.contains(productId)) {
            //currentFavIds.filter { it != productId }.toSet()
            currentFavIds - productId
            //this scope mean user deselected favorite
        } else {
            //this scope mean user select favorite
            currentFavIds + productId //set of int + int will generate new set of int
        }
        return currentState.copy(favoriteProductIds = newFavoriteIds)
    }
}