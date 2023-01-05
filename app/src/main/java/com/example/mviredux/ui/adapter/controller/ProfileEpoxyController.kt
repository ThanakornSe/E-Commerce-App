package com.example.mviredux.ui.adapter.controller

import com.airbnb.epoxy.TypedEpoxyController
import com.example.mviredux.R
import com.example.mviredux.model.domain.user.User
import com.example.mviredux.redux.ApplicationState
import com.example.mviredux.redux.reducer.UserProfileItemGenerator
import com.example.mviredux.ui.adapter.model.DividerEpoxyModel
import com.example.mviredux.ui.adapter.model.SignedInItemEpoxyModel
import com.example.mviredux.ui.adapter.model.SignedOutEpoxyModel
import com.example.mviredux.utils.AppConst.toPx

class ProfileEpoxyController(
    private val userProfileItemGenerator: UserProfileItemGenerator,
    val onSignIn: (String, String) -> Unit,
    val onClick: (Int) -> Unit
) : TypedEpoxyController<ApplicationState.AuthState?>() {

    override fun buildModels(data: ApplicationState.AuthState?) {
        when (data) {
            is ApplicationState.AuthState.UnAuthenticated -> {
                SignedOutEpoxyModel(onSignIn = onSignIn, errorMessage = data.errorString).id("signed_out_state").addTo(this)
            }
            is ApplicationState.AuthState.Authenticated -> {
                userProfileItemGenerator.buildItems(user = data.user).forEach { profileItem ->
                    SignedInItemEpoxyModel(
                        iconRes = profileItem.iconRes,
                        headerText = profileItem.headerText,
                        infoText = profileItem.infoText,
                        onClick = onClick
                    ).id(profileItem.iconRes).addTo(this)

                    DividerEpoxyModel(
                        horizontalMargin = 20.toPx()
                    ).id("divider_${profileItem.iconRes}").addTo(this)
                }

                SignedInItemEpoxyModel(
                    iconRes = R.drawable.ic_round_logout_24,
                    headerText = "Logout",
                    infoText = "Sign out of your account",
                    onClick = onClick
                ).id(R.drawable.ic_round_logout_24).addTo(this)
            }
            else -> {

            }

        }
    }
}