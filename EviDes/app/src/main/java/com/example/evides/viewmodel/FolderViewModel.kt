package com.example.evides.viewmodel

import androidx.lifecycle.ViewModel
import com.example.evides.domain.model.Evidence
import com.example.evides.domain.model.EvidenceType
import com.example.evides.domain.repository.EvidenceRepository
import com.example.evides.util.CiUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FolderViewModel(
    private val repository: EvidenceRepository,
    private val ciNumber: String
) : ViewModel() {

    // null means "show all"
    private val _filter = MutableStateFlow<EvidenceType?>(null)
    val filter: StateFlow<EvidenceType?> = _filter.asStateFlow()

    private val _evidences = MutableStateFlow<List<Evidence>>(emptyList())
    val evidences: StateFlow<List<Evidence>> = _evidences.asStateFlow()

    private val _selectedIds = MutableStateFlow<Set<Long>>(emptySet())
    val selectedIds: StateFlow<Set<Long>> = _selectedIds.asStateFlow()

    private val _showDeleteDialog = MutableStateFlow(false)
    val showDeleteDialog: StateFlow<Boolean> = _showDeleteDialog.asStateFlow()

    val folderName: String
        get() = CiUtils.buildName(ciNumber)

    init {
        loadEvidences()
    }

    private fun loadEvidences() {
        _evidences.value = repository.getEvidences(ciNumber, _filter.value)
    }

    fun onFilterChange(type: EvidenceType?) {
        _filter.value = type
        loadEvidences()
    }

    fun addEvidence(
        type: EvidenceType,
        uri: String,
        latitude: Double?,
        longitude: Double?
    ) {
        repository.addEvidence(
            Evidence(
                id = 0,
                ciNumber = ciNumber,
                type = type,
                uri = uri,
                latitude = latitude,
                longitude = longitude,
                createdAt = System.currentTimeMillis()
            )
        )
        loadEvidences()
    }

    fun onToggleSelection(id: Long) {
        val current = _selectedIds.value
        _selectedIds.value = if (id in current) current - id else current + id
    }

    fun onClearSelection() {
        _selectedIds.value = emptySet()
    }

    fun onDeleteRequest() {
        if (_selectedIds.value.isNotEmpty()) {
            _showDeleteDialog.value = true
        }
    }

    fun onConfirmDelete() {
        _selectedIds.value.forEach { repository.deleteEvidence(it) }
        _selectedIds.value = emptySet()
        _showDeleteDialog.value = false
        loadEvidences()
    }

    fun onDismissDeleteDialog() {
        _showDeleteDialog.value = false
    }
}