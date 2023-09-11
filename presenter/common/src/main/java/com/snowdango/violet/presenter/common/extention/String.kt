package com.snowdango.violet.presenter.common.extention

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64

fun String.toBitmap(): Bitmap {
    val decodedString = Base64.decode(this.substring(this.indexOf(",") + 1), Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
}