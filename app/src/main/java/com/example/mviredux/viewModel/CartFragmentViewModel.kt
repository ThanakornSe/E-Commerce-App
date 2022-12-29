package com.example.mviredux.viewModel

import androidx.lifecycle.ViewModel
import com.example.mviredux.redux.ApplicationState
import com.example.mviredux.redux.Store
import com.example.mviredux.redux.reducer.UIProductListReducer
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartFragmentViewModel @Inject constructor(
    val store:Store<ApplicationState>,
    val uiProductListReducer: UIProductListReducer
):ViewModel() {
}