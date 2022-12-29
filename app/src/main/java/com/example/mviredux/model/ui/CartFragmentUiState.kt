package com.example.mviredux.model.ui

import com.androidfactory.fakestore.model.domain.Product

sealed interface CartFragmentUiState {
    object Empty:CartFragmentUiState
    data class NonEmpty(val products:List<UiProduct>):CartFragmentUiState
}