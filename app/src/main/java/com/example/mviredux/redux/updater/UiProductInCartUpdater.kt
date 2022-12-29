package com.example.mviredux.redux.updater

import com.example.mviredux.redux.ApplicationState
import javax.inject.Inject

class UiProductInCartUpdater @Inject constructor() {

    fun onProductInCartUpdate(productId:Int, currentState: ApplicationState): ApplicationState {
        val currentInCartIds = currentState.inCartProductIds
        val newInCartIds: Set<Int> = if (currentInCartIds.contains(productId)) {
            currentInCartIds - productId
        } else {
            currentInCartIds + productId
        }
        return currentState.copy(inCartProductIds = newInCartIds)
    }
}