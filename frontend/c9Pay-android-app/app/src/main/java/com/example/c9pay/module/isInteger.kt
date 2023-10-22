package com.example.c9pay.module

fun isInteger(string: String): Boolean {
    var isInteger = true

    string.forEach { char ->
        val charConvertedToCode = char.code

        if (charConvertedToCode > 57 || charConvertedToCode < 48) {
            isInteger = false
            return isInteger
        }
    }

    return isInteger
}