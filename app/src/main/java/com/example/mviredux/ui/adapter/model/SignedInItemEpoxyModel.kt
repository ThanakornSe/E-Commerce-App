package com.example.mviredux.ui.adapter.model

import androidx.annotation.DrawableRes
import com.example.mviredux.R
import com.example.mviredux.databinding.EpoxyModelProfileSignedInItemBinding
import com.example.mviredux.utils.ViewBindingKotlinModel

data class SignedInItemEpoxyModel(
    @DrawableRes val iconRes: Int,
    val headerText: String,
    val infoText: String,
    val onClick: (Int) -> Unit
) : ViewBindingKotlinModel<EpoxyModelProfileSignedInItemBinding>(R.layout.epoxy_model_profile_signed_in_item) {

    override fun EpoxyModelProfileSignedInItemBinding.bind() {
        iconImageView.setImageResource(iconRes)
        headerTextView.text = headerText
        infoTextView.text = infoText
        root.setOnClickListener { onClick(iconRes) }
    }
}