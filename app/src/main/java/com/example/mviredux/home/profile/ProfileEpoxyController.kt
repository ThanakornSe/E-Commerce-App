package com.example.mviredux.home.profile

import com.airbnb.epoxy.TypedEpoxyController
import com.androidfactory.fakestore.extensions.toPx
import com.example.mviredux.R
import com.example.mviredux.home.cart.DividerEpoxyModel
import com.example.mviredux.redux.ApplicationState

class ProfileEpoxyController(
    private val userProfileItemGenerator: UserProfileItemGenerator,
    private val profileUiActions: ProfileUiActions
) : TypedEpoxyController<ApplicationState.AuthState?>() {

    override fun buildModels(data: ApplicationState.AuthState?) {
        when (data) {
            is ApplicationState.AuthState.UnAuthenticated -> {
                SignedOutEpoxyModel(onSignIn = ::onSignIn, errorMessage = data.errorString).id("signed_out_state").addTo(this)
            }
            is ApplicationState.AuthState.Authenticated -> {
                userProfileItemGenerator.buildItems(user = data.user).forEach { profileItem ->
                    SignedInItemEpoxyModel(
                        iconRes = profileItem.iconRes,
                        headerText = profileItem.headerText,
                        infoText = profileItem.infoText,
                        onClick = ::onClick
                    ).id(profileItem.iconRes).addTo(this)

                    DividerEpoxyModel(
                        horizontalMargin = 20.toPx()
                    ).id("divider_${profileItem.iconRes}").addTo(this)
                }

                SignedInItemEpoxyModel(
                    iconRes = R.drawable.ic_round_logout_24,
                    headerText = "Logout",
                    infoText = "Sign out of your account",
                    onClick = ::onClick
                ).id(R.drawable.ic_round_logout_24).addTo(this)
            }
            else -> {

            }

        }
    }

    private fun onSignIn(username:String, password:String) {
        profileUiActions.onSignIn(username,password)
    }

    private fun onClick(resId:Int) {
        profileUiActions.onProfileItemSelected(resId)
    }
}