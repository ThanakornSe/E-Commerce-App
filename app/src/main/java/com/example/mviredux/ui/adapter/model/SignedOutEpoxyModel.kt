package com.example.mviredux.ui.adapter.model

import com.example.mviredux.R
import com.example.mviredux.databinding.EpoxyModelProfileSignedOutBinding
import com.example.mviredux.utils.ViewBindingKotlinModel

data class SignedOutEpoxyModel(
    val onSignIn: (String, String) -> Unit
) : ViewBindingKotlinModel<EpoxyModelProfileSignedOutBinding>(R.layout.epoxy_model_profile_signed_out) {

    override fun EpoxyModelProfileSignedOutBinding.bind() {
        button.setOnClickListener {
            onSignIn("donero", "ewedon")
        }
    }
}