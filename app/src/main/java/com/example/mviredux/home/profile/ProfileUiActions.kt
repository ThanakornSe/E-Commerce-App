package com.example.mviredux.home.profile

import android.util.Log
import com.example.mviredux.R
import com.example.mviredux.viewModel.AuthViewModel

class ProfileUiActions(private val viewModel: AuthViewModel) {

    fun onSignIn(username: String, password: String) {
        viewModel.login(username, password)
    }

    fun onProfileItemSelected(id: Int) {
        when (id) {
            R.drawable.ic_round_phone_24 -> {
                viewModel.sendCallIntent()
            }
            R.drawable.ic_round_location_24 -> {
                viewModel.sendLocationIntent()
            }
            R.drawable.ic_round_logout_24 -> {
                viewModel.logout()
            }
            else -> {
                Log.i("SELECTION", "Unknown ID -> $id")
            }
        }
    }
}