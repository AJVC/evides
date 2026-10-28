package com.example.evides.domain.model

data class Evidence(
    val id: Long,
    val ciNumber: String,
    val type: EvidenceType,
    val uri: String,
    val latitude: Double?,
    val longitude: Double?,
    val createdAt: Long
)