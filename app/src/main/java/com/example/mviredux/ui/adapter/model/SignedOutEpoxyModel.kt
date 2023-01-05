package com.example.mviredux.ui.adapter.model

import android.view.View
import com.example.mviredux.R
import com.example.mviredux.databinding.EpoxyModelProfileSignedOutBinding
import com.example.mviredux.utils.ViewBindingKotlinModel

data class SignedOutEpoxyModel(
    val onSignIn: (String, String) -> Unit,
    val errorMessage: String?
) : ViewBindingKotlinModel<EpoxyModelProfileSignedOutBinding>(R.layout.epoxy_model_profile_signed_out) {

    override fun EpoxyModelProfileSignedOutBinding.bind() {
        passwordLayout.error = errorMessage
        signInButton.setOnClickListener {
            val username = usernameEditText.text?.toString()
            val password = passwordEditText.text?.toString()

            if (username.isNullOrBlank() || password.isNullOrBlank()) {
                passwordLayout.error = "Both fields required"
                return@setOnClickListener
            }

            passwordLayout.error = null
            onSignIn(username, password)
        }
    }

}