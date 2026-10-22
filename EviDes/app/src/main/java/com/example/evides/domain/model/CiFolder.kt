package com.example.evides.domain.model

data class CiFolder(
    val number: String,
    val createdAt: Long
) {
    val name: String
        get() = "CI-$number"
}