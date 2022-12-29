package com.example.mviredux.utils

import android.content.res.Resources
import androidx.annotation.Dimension
import java.util.*

object AppConst {
    const val BASE_URL = "https://fakestoreapi.com/"

    @Dimension(unit = Dimension.DP)
    fun Int.toDp():Int = (this/Resources.getSystem().displayMetrics.density).toInt()

    @Dimension(unit = Dimension.PX)
    fun Int.toPx():Int = (this/Resources.getSystem().displayMetrics.density).toInt()
}