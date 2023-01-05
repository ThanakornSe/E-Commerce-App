package com.example.mviredux.model.mapper

import com.example.mviredux.model.domain.user.Address
import com.example.mviredux.model.domain.user.Name
import com.example.mviredux.model.domain.user.User
import com.example.mviredux.model.network.NetworkUser
import com.example.mviredux.utils.AppConst.capitalize
import javax.inject.Inject

class UserMapper @Inject constructor() {

    fun buildFrom(networkUser: NetworkUser): User {
        return User(
            id = networkUser.id,
            name = Name(
                firstname = networkUser.name.firstname.capitalize(),
                lastname = networkUser.name.lastname.capitalize()
            ),
            email = networkUser.email,
            username = networkUser.username,
            phoneNumber = networkUser.phone,
            address = Address(
                city = networkUser.address.city,
                number = networkUser.address.number,
                street = networkUser.address.street,
                zipcode = networkUser.address.zipcode.split("-")[0],
                lat = networkUser.address.geolocation.lat,
                long = networkUser.address.geolocation.long
            )
        )
    }
}