package com.example.mviredux.viewModel

import androidx.lifecycle.ViewModel
import com.example.mviredux.redux.ApplicationState
import com.example.mviredux.redux.Store
import com.example.mviredux.redux.reducer.UIProductListReducer
import com.example.mviredux.redux.updater.UiProductFavoriteUpdater
import com.example.mviredux.redux.updater.UiProductInCartUpdater
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartFragmentViewModel @Inject constructor(
    val store:Store<ApplicationState>,
    val uiProductListReducer: UIProductListReducer,
    val uiProductFavoriteUpdater: UiProductFavoriteUpdater,
    val uiProductInCartUpdater: UiProductInCartUpdater
):ViewModel() {
}