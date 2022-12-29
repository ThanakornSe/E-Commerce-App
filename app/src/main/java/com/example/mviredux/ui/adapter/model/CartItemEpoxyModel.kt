package com.example.mviredux.ui.adapter.model

import android.view.ViewGroup
import androidx.annotation.Dimension
import androidx.core.view.updateLayoutParams
import coil.load
import com.example.mviredux.R
import com.example.mviredux.databinding.EpoxyModelCartProductItemBinding
import com.example.mviredux.model.ui.UiProduct
import com.example.mviredux.utils.ViewBindingKotlinModel

data class CartItemEpoxyModel(
    private val uiProduct: UiProduct,
    @Dimension(unit = Dimension.PX) private val horizontalMargin: Int,
    private val onFavoriteClicked: (Int) -> Unit,
    private val onDeleteClicked: (Int) -> Unit
) : ViewBindingKotlinModel<EpoxyModelCartProductItemBinding>(R.layout.epoxy_model_cart_product_item) {

    override fun EpoxyModelCartProductItemBinding.bind() {
        // Setup our text
        productTitleTextView.text = uiProduct.product.title

        // Favorite icon
        val imageRes = if (uiProduct.isFavorite) {
            R.drawable.ic_round_favorite_24
        } else {
            R.drawable.ic_round_favorite_border_24
        }
        favoriteImageView.setIconResource(imageRes)
        favoriteImageView.setOnClickListener { onFavoriteClicked(uiProduct.product.id) }

        deleteIconImageView.setOnClickListener { onDeleteClicked(uiProduct.product.id) }

        // Load our image
        productImageView.load(data = uiProduct.product.image)

        root.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            setMargins(horizontalMargin, 0, horizontalMargin, 0)
        }

        quantityView.apply {
            quantityTextView.text = 9.toString()
            minusImageView.setOnClickListener {  }
            plusImageView.setOnClickListener {  }
        }
    }
}