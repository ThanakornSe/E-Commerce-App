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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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
                applicationState.copy(
                    user = donUserResponse.body()?.let { userResponse ->
                        userMapper.buildFrom(userResponse)
                    }
                )
            }

            if (donUserResponse.body() == null) {
                Log.e("Login", response.errorBody()?.toString() ?: response.message())
            }
        } else {
            Log.e(
                "Login",
                response.errorBody()?.byteStream()?.bufferedReader()?.readLine() ?: "Invalid Login"
            )
        }
    }

}