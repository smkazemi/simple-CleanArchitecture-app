package com.basalam.intern.android.util

import android.app.Activity
import android.widget.Toast
import timber.log.Timber

fun String.toLog(flag: String) {
    Timber.tag("12TAG").d("$flag -> $this")
}

fun Activity.shortToast(mes: String) {
    Toast.makeText(this, mes, Toast.LENGTH_SHORT).show()
}


fun <I, O> List<I>.listMapper(transformer: (I) -> O): List<O> {

    return this.map {
        transformer(it)
    }

}