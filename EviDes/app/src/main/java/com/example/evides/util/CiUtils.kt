package com.example.evides.util

object CiUtils {

    const val PREFIX = "CI-"

    fun buildName(number: String): String = "$PREFIX$number"

    fun sanitize(input: String): String = input.filter { it.isDigit() }
}