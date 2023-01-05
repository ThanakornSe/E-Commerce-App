package com.example.mviredux.utils

import android.content.res.Resources
import androidx.annotation.Dimension
import com.example.mviredux.utils.AppConst.capitalize
import okhttp3.ResponseBody
import java.text.NumberFormat
import java.util.*

object AppConst {
    const val BASE_URL = "https://fakestoreapi.com/"

    @Dimension(unit = Dimension.DP)
    fun Int.toDp():Int = (this/Resources.getSystem().displayMetrics.density).toInt()

    @Dimension(unit = Dimension.PX)
    fun Int.toPx():Int = (this/Resources.getSystem().displayMetrics.density).toInt()

    val currencyFormatter: NumberFormat = NumberFormat.getCurrencyInstance()

    fun String.capitalize(): String {
        return this.replaceFirstChar {
            if (it.isLowerCase()) {
                it.titlecase(Locale.getDefault())
            } else it.toString()
        }
    }

    fun ResponseBody?.parseError(): String? {
        return this?.byteStream()?.bufferedReader()?.readLine()?.capitalize()
    }
}