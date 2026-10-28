package com.example.evides.domain.model

import com.example.evides.util.CiUtils

data class CiFolder(
    val number: String,
    val createdAt: Long
) {
    val name: String
        get() = CiUtils.buildName(number)
}