package com.example.mviredux.ui.adapter.model

import android.view.View
import com.example.mviredux.R
import com.example.mviredux.databinding.EpoxyModelCartEmptyBinding
import com.example.mviredux.utils.ViewBindingKotlinModel

data class CartEmptyEpoxyModel(
    private val onClick: (View) -> Unit
) : ViewBindingKotlinModel<EpoxyModelCartEmptyBinding>(R.layout.epoxy_model_cart_empty) {

    override fun EpoxyModelCartEmptyBinding.bind() {
        button.setOnClickListener(onClick)
    }
}