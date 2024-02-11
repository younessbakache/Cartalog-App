package com.vitor238.cartalog.utils

import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.text(): String {
    val text = this.editText?.text.toString()
    return if (text == "null") "" else text
}