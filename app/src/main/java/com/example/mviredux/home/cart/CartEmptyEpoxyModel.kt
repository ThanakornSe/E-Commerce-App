package com.example.mviredux.home.cart

import com.example.mviredux.R
import com.example.mviredux.databinding.EpoxyModelCartEmptyBinding
import com.example.mviredux.utils.ViewBindingKotlinModel

data class CartEmptyEpoxyModel(
    private val onClick: () -> Unit
) : ViewBindingKotlinModel<EpoxyModelCartEmptyBinding>(R.layout.epoxy_model_cart_empty) {

    override fun EpoxyModelCartEmptyBinding.bind() {
        button.setOnClickListener {
            onClick()
        }
    }
}
