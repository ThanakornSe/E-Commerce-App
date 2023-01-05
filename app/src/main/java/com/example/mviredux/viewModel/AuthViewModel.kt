package com.example.mviredux.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mviredux.model.mapper.UserMapper
import com.example.mviredux.model.network.LoginResponse
import com.example.mviredux.model.network.NetworkUser
import com.example.mviredux.redux.ApplicationState
import com.example.mviredux.redux.Store
import com.example.mviredux.repository.AuthRepository
import com.example.mviredux.utils.AppConst.capitalize
import com.example.mviredux.utils.AppConst.parseError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    val store: Store<ApplicationState>,
    private val authRepository: AuthRepository,
    private val userMapper: UserMapper
) : ViewModel() {

    fun login(username: String, password: String) = viewModelScope.launch {
        val response: Response<LoginResponse> = authRepository.login(username, password)
        if (response.isSuccessful) {
            val donUserResponse: Response<NetworkUser> = authRepository.fetchUser()
            store.update { applicationState ->
                val authState = donUserResponse.body()?.let { body ->
                    ApplicationState.AuthState.Authenticated(user = userMapper.buildFrom(body))
                }?: ApplicationState.AuthState.UnAuthenticated(
                    errorString = response.errorBody()?.parseError()
                )

                return@update  applicationState.copy(authState = authState)
            }
        } else {
            store.update { applicationState ->
                applicationState.copy(
                    authState = ApplicationState.AuthState.UnAuthenticated(
                        errorString = response.errorBody()?.parseError()
                    )
                )
            }
        }
    }

    fun logout() = viewModelScope.launch {
        store.update { applicationState -> applicationState.copy(authState = ApplicationState.AuthState.UnAuthenticated()) }
        // traditionally make a call the the BE
    }

}