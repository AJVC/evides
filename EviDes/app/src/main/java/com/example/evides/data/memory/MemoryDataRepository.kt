package com.example.evides.data.memory

import com.example.evides.domain.model.CiFolder
import com.example.evides.domain.model.Evidence
import com.example.evides.domain.model.EvidenceType
import com.example.evides.domain.repository.EvidenceRepository

class MemoryDataRepository : EvidenceRepository {

    private val folders = mutableListOf<CiFolder>()
    private val evidences = mutableListOf<Evidence>()
    private var nextId = 1L

    override fun getFolder(number: String): CiFolder? =
        folders.find { it.number == number }

    override fun createFolder(number: String): CiFolder {
        val folder = CiFolder(
            number = number,
            createdAt = System.currentTimeMillis()
        )
        folders.add(folder)
        return folder
    }

    override fun getEvidences(number: String, type: EvidenceType?): List<Evidence> =
        evidences.filter { it.ciNumber == number && (type == null || it.type == type) }

    override fun addEvidence(evidence: Evidence): Evidence {
        val saved = evidence.copy(id = nextId++)
        evidences.add(saved)
        return saved
    }

    override fun updateEvidence(evidence: Evidence): Boolean {
        val index = evidences.indexOfFirst { it.id == evidence.id }
        if (index == -1) return false
        evidences[index] = evidence
        return true
    }

    override fun deleteEvidence(id: Long): Boolean =
        evidences.removeAll { it.id == id }
}