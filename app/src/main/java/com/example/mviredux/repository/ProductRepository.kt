package com.example.mviredux.repository

import com.androidfactory.fakestore.model.domain.Product
import com.androidfactory.fakestore.model.mapper.ProductMapper
import com.example.mviredux.di.network.ProductsServices
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productsService: ProductsServices,
    private val productMapper: ProductMapper
) {
    suspend fun fetchAllProducts(): List<Product> {
        // todo better error handling
        return productsService.getAllProducts().body()?.let { networkProducts ->
            networkProducts.map { productMapper.buildFrom(it) }
        } ?: emptyList()
    }
}