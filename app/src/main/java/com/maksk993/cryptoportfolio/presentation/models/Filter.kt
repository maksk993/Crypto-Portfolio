package com.maksk993.cryptoportfolio.presentation.models

import android.text.InputFilter

const val DIGITS_MAX_COUNT = 9
const val DIGITS_MAX_COUNT_AFTER_DOT = 5

object Filter {
    private val filter = InputFilter{
        source, start, end, dest, dstart, dend ->
        if (dest.toString().length >= DIGITS_MAX_COUNT) {
            return@InputFilter ""
        }
        else if (dest.toString().contains(".")){
            val dotIndex = dest.toString().indexOf(".")
            if (dest.toString()
                .substring(dotIndex, dest.toString().length).length > DIGITS_MAX_COUNT_AFTER_DOT)
                return@InputFilter ""
        }
        null
    }

    fun execute() : InputFilter = filter
}