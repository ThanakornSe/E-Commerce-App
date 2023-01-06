package com.example.mviredux.repository

import com.example.mviredux.model.domain.user.User
import com.example.mviredux.model.mapper.UserMapper
import com.example.mviredux.model.network.LoginPostBody
import com.example.mviredux.model.network.LoginResponse
import com.example.mviredux.model.network.NetworkUser
import com.example.mviredux.di.network.AuthServices
import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authServices: AuthServices
) {

    suspend fun login(username:String, password:String):Response<LoginResponse> {
        return authServices.login(LoginPostBody(username,password))
    }

    suspend fun fetchUser():Response<NetworkUser> {
        return authServices.fetchUser(userId = 2)
    }
}