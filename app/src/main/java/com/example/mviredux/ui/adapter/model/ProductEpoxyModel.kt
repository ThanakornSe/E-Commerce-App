package com.example.mviredux.ui.adapter.model

import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import coil.load
import com.example.mviredux.R
import com.example.mviredux.databinding.EpoxyModelProductItemBinding
import com.example.mviredux.model.ui.UiProduct
import com.example.mviredux.utils.ViewBindingKotlinModel
import java.text.NumberFormat
import kotlin.math.roundToInt

data class ProductEpoxyModel(
    val uiProduct: UiProduct?,
    val onFavoriteIconClicked:(Int) -> Unit,
    val onAddToCartClicked:(Int) -> Unit,
    val onProductClicked:(Int) -> Unit
) : ViewBindingKotlinModel<EpoxyModelProductItemBinding>(R.layout.epoxy_model_product_item) {

    private val currencyFormatter: NumberFormat = NumberFormat.getCurrencyInstance()

    override fun EpoxyModelProductItemBinding.bind() {
        shimmerLayout.isVisible = uiProduct == null
        cardView.isInvisible = uiProduct == null

        uiProduct?.let { ui ->
            shimmerLayout.stopShimmer()

            // Setup our text
            productTitleTextView.text = ui.product.title
            productDescriptionTextView.text = ui.product.description
            productCategoryTextView.text = ui.product.category
            productPriceTextView.text = currencyFormatter.format(ui.product.price)

            // Favorite icon
            val imageRes = if (ui.isFavorite) {
                R.drawable.ic_round_favorite_24
            } else {
                R.drawable.ic_round_favorite_border_24
            }
            favoriteImageView.setIconResource(imageRes)
            favoriteImageView.setOnClickListener {
                onFavoriteIconClicked(ui.product.id)
            }


            // In Cart Icon
            inCartView.isVisible = ui.isInCart
            addToCartButton.setOnClickListener {
                onAddToCartClicked(ui.product.id)
            }

            //product Description visibility
            productDescriptionTextView.isVisible = ui.isExpanded
            root.setOnClickListener {
                onProductClicked(ui.product.id)
            }

            // Load our image
            productImageViewLoadingProgressBar.isVisible = true
            productImageView.load(data = ui.product.image) {
                listener { request, result ->
                    productImageViewLoadingProgressBar.isGone = true
                }
            }

            ratingIndicator.progress = (uiProduct.product.rating.value*10).roundToInt()
            ratingTextView.text = "${uiProduct.product.rating.value}"
        } ?: shimmerLayout.startShimmer()
    }
}
