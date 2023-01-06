package com.example.mviredux.home.cart

import com.airbnb.epoxy.TypedEpoxyController
import com.androidfactory.fakestore.extensions.toPx
import com.example.mviredux.model.ui.CartFragmentUiState
import com.example.mviredux.utils.VerticalSpaceEpoxyModel

class CartFragmentEpoxyController(
    private val onEmptyCardClicked:() -> Unit,
    private val onFavoriteClicked: (Int) -> Unit,
    private val onDeleteClicked: (Int) -> Unit,
    private val onQuantityChange:(Int,Int) -> Unit
) : TypedEpoxyController<CartFragmentUiState>() {
    override fun buildModels(data: CartFragmentUiState?) {
        when (data) {
            null, is CartFragmentUiState.Empty -> {
                CartEmptyEpoxyModel(onClick = onEmptyCardClicked).id("empty_state").addTo(this)
            }
            is CartFragmentUiState.NonEmpty -> {
                data.products.forEachIndexed { index, uiProduct ->
                    addVerticalStyling(index)
                    CartItemEpoxyModel(
                        uiProductInCart = uiProduct,
                        24.toPx(),
                        onFavoriteClicked,
                        onDeleteClicked,
                        onQuantityChange
                    ).id(uiProduct.uiProduct.product.id).addTo(this)
                }
            }
        }
    }

    private fun addVerticalStyling(index: Int) {
        VerticalSpaceEpoxyModel(8.toPx()).id("top_space_$index").addTo(this)

        if (index != 0) {
            DividerEpoxyModel(horizontalMargin = 16.toPx()).id("divider_$index").addTo(this)
        }
        VerticalSpaceEpoxyModel(8.toPx()).id("bottom_space_$index").addTo(this)
    }
}