package com.example.mviredux.ui.adapter.model

import android.view.ViewGroup
import androidx.annotation.Dimension
import androidx.core.view.updateLayoutParams
import coil.load
import com.example.mviredux.R
import com.example.mviredux.databinding.EpoxyModelCartProductItemBinding
import com.example.mviredux.model.ui.UiProductInCart
import com.example.mviredux.utils.ViewBindingKotlinModel
import java.math.BigDecimal
import java.text.NumberFormat

data class CartItemEpoxyModel(
    val uiProductInCart: UiProductInCart,
    @Dimension(unit = Dimension.PX) private val horizontalMargin: Int,
    private val onFavoriteClicked: (Int) -> Unit,
    private val onDeleteClicked: (Int) -> Unit,
    private val onQuantityChange: (Int, Int) -> Unit
) : ViewBindingKotlinModel<EpoxyModelCartProductItemBinding>(R.layout.epoxy_model_cart_product_item) {

    private val currencyFormatter: NumberFormat = NumberFormat.getCurrencyInstance()

    override fun EpoxyModelCartProductItemBinding.bind() {
        swipeToDismissTextView.translationX = 0f
        // Setup our text
        productTitleTextView.text = uiProductInCart.uiProduct.product.title

        // Favorite icon
        val imageRes = if (uiProductInCart.uiProduct.isFavorite) {
            R.drawable.ic_round_favorite_24
        } else {
            R.drawable.ic_round_favorite_border_24
        }
        favoriteImageView.setIconResource(imageRes)
        favoriteImageView.setOnClickListener { onFavoriteClicked(uiProductInCart.uiProduct.product.id) }

        deleteIconImageView.setOnClickListener { onDeleteClicked(uiProductInCart.uiProduct.product.id) }

        // Load our image
        productImageView.load(data = uiProductInCart.uiProduct.product.image)

        root.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            setMargins(horizontalMargin, 0, horizontalMargin, 0)
        }

        quantityView.apply {
            quantityTextView.text = uiProductInCart.quantity.toString()
            minusImageView.setOnClickListener {
                onQuantityChange(
                    uiProductInCart.uiProduct.product.id,
                    uiProductInCart.quantity - 1
                )
            }
            plusImageView.setOnClickListener {
                onQuantityChange(
                    uiProductInCart.uiProduct.product.id,
                    uiProductInCart.quantity + 1
                )
            }
        }

        val totalPrice = uiProductInCart.uiProduct.product.price * BigDecimal(uiProductInCart.quantity)
        tvTotalProductPrice.text = currencyFormatter.format(totalPrice)
    }
}