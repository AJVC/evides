package com.example.evides.viewmodel

import androidx.lifecycle.ViewModel
import com.example.evides.data.memory.MemoryDataRepository
import com.example.evides.domain.model.Evidence
import com.example.evides.domain.repository.EvidenceRepository

class EvidenceCrudViewModel : ViewModel() {
    companion object {
        private var repository: EvidenceRepository

        init {
            repository = MemoryDataRepository()
        }

        fun getEvidenceById(id: Long): Evidence? {
            return repository.getEvidenceById(id)
        }
        fun getEvidencesByCi(ci: String): List<Evidence> {
            return repository.getEvidences(ci)
        }

        fun saveEvidence(evidence: Evidence): Evidence {
            return repository.addEvidence(evidence)
        }

        fun updateEvidence(evidence: Evidence): Boolean {
            return repository.updateEvidence(evidence)
        }

        fun removeEvidence(id: Long): Boolean {
            return repository.deleteEvidence(id)
        }
    }
}