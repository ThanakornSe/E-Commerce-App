package com.example.mviredux.adapter.model

import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import coil.load
import com.androidfactory.fakestore.model.domain.Product
import com.example.mviredux.R
import com.example.mviredux.databinding.EpoxyModelProductItemBinding
import com.example.mviredux.utils.ViewBindingKotlinModel
import java.text.NumberFormat

data class ProductEpoxyModel(
    val product: Product?
) : ViewBindingKotlinModel<EpoxyModelProductItemBinding>(R.layout.epoxy_model_product_item) {

    private val currencyFormatter: NumberFormat = NumberFormat.getCurrencyInstance()

    override fun EpoxyModelProductItemBinding.bind() {
        shimmerLayout.isVisible = product == null
        cardView.isInvisible = product == null

        product?.let { product ->
            shimmerLayout.stopShimmer()

            // Setup our text
            productTitleTextView.text = product.title
            productDescriptionTextView.text = product.description
            productCategoryTextView.text = product.category
            productPriceTextView.text = currencyFormatter.format(product.price)

            // Load our image
            productImageViewLoadingProgressBar.isVisible = true
            productImageView.load(data = product.image) {
                listener { request, result ->
                    productImageViewLoadingProgressBar.isGone = true
                }
            }
        } ?: shimmerLayout.startShimmer()
    }
}
