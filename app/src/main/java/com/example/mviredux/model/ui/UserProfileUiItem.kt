package com.example.mviredux.model.ui

import androidx.annotation.DrawableRes

data class UserProfileUiItem(
    @DrawableRes val iconRes: Int,
    val headerText: String,
    val infoText: String
)