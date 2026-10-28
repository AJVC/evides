package com.example.evides.domain.repository

import com.example.evides.domain.model.CiFolder
import com.example.evides.domain.model.Evidence
import com.example.evides.domain.model.EvidenceType

interface EvidenceRepository {

    // Folders
    fun getFolder(number: String): CiFolder?
    fun createFolder(number: String): CiFolder

    // Evidences
    fun getEvidences(number: String, type: EvidenceType? = null): List<Evidence>
    fun addEvidence(evidence: Evidence): Evidence
    fun updateEvidence(evidence: Evidence): Boolean
    fun deleteEvidence(id: Long): Boolean
}