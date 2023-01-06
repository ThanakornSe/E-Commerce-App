package com.androidfactory.fakestore.model.mapper

import com.androidfactory.fakestore.extensions.capitalize
import com.androidfactory.fakestore.model.domain.Product
import com.example.mviredux.model.network.NetworkProduct
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

class ProductMapper @Inject constructor() {
    //@Inject constructor mean we can use this class anywhere in this app

    fun buildFrom(networkProduct: NetworkProduct): Product {
        return Product(
            category = networkProduct.category.capitalize(),
            description = networkProduct.description,
            id = networkProduct.id,
            image = networkProduct.image,
            price = BigDecimal(networkProduct.price).setScale(2, RoundingMode.HALF_UP),
            title = networkProduct.title,
            rating = Product.Rating(
                value = networkProduct.rating.rate,
                numberOfRating = networkProduct.rating.count
            )
        )
    }


}