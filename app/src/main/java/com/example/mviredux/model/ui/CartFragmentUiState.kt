package com.example.mviredux.model.ui

sealed interface CartFragmentUiState {
    object Empty:CartFragmentUiState
    data class NonEmpty(val products:List<UiProductInCart>):CartFragmentUiState
}