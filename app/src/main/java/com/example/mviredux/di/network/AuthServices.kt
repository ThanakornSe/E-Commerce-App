package com.example.mviredux.di.network

import com.example.mviredux.model.network.LoginPostBody
import com.example.mviredux.model.network.LoginResponse
import com.example.mviredux.model.network.NetworkUser
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthServices {

    @POST("auth/login")
    suspend fun login(
        @Body postBody: LoginPostBody
    ):Response<LoginResponse>

    @GET("users/{user-id}")
    suspend fun fetchUser(
        @Path("user-id") userId: Int
    ): Response<NetworkUser>
}