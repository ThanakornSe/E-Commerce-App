package com.example.mviredux.network

import com.example.mviredux.utils.AppConst
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface ProductsServices {

    @GET("products")
    suspend fun getAllProducts():Response<List<Any>>

//    companion object ShopApi {
//        val productsServices: ProductsServices = retrofit.create(ProductsServices::class.java)
//    }
}