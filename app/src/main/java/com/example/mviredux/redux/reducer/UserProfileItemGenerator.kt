package com.example.mviredux.redux.reducer

import androidx.annotation.DrawableRes
import com.example.mviredux.R
import com.example.mviredux.model.domain.user.User
import com.example.mviredux.model.ui.UserProfileUiItem
import javax.inject.Inject

class UserProfileItemGenerator @Inject constructor() {

    fun buildItems(user: User): List<UserProfileUiItem> {
        return buildList {
            add(
                UserProfileUiItem(
                    iconRes = R.drawable.ic_person_24,
                    headerText = "Username",
                    infoText = user.username
                )
            )
            add(
                UserProfileUiItem(
                    iconRes = R.drawable.ic_round_phone_24,
                    headerText = "Phone number",
                    infoText = user.phoneNumber
                )
            )
            add(
                UserProfileUiItem(
                    iconRes = R.drawable.ic_round_location_24,
                    headerText = "Location",
                    infoText = "${user.address.street}, ${user.address.city}, ${user.address.zipcode}"
                )
            )
        }
    }
}