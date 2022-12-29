package com.example.mviredux.ui.adapter.controller

import com.airbnb.epoxy.TypedEpoxyController
import com.example.mviredux.model.ui.CartFragmentUiState
import com.example.mviredux.ui.adapter.model.CartEmptyEpoxyModel
import com.example.mviredux.ui.adapter.model.CartItemEpoxyModel
import com.example.mviredux.ui.adapter.model.DividerEpoxyModel
import com.example.mviredux.utils.AppConst.toPx
import com.example.mviredux.utils.VerticalSpaceEpoxyModel

class CartFragmentEpoxyController(
    private val onEmptyCardClicked:() -> Unit,
    private val onFavoriteClicked: (Int) -> Unit,
    private val onDeleteClicked: (Int) -> Unit
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
                        uiProduct = uiProduct,
                        24.toPx(),
                        onFavoriteClicked,
                        onDeleteClicked
                    ).id(uiProduct.product.id).addTo(this)
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