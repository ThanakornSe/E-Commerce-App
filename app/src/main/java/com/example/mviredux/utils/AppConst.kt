package com.example.mviredux.utils

import okhttp3.ResponseBody
import java.text.NumberFormat

object AppConst {
    const val BASE_URL = "https://fakestoreapi.com/"

    val currencyFormatter: NumberFormat = NumberFormat.getCurrencyInstance()

    fun ResponseBody?.parseError(): String? {
        return this?.byteStream()?.bufferedReader()?.readLine()?.capitalize()
    }
}