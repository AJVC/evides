package com.example.evides.viewmodel

import androidx.lifecycle.ViewModel
import com.example.evides.domain.repository.EvidenceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel(
    private val repository: EvidenceRepository
) : ViewModel() {

    private val _number = MutableStateFlow("")
    val number: StateFlow<String> = _number.asStateFlow()

    private val _showExistsDialog = MutableStateFlow(false)
    val showExistsDialog: StateFlow<Boolean> = _showExistsDialog.asStateFlow()

    private val _showCreateDialog = MutableStateFlow(false)
    val showCreateDialog: StateFlow<Boolean> = _showCreateDialog.asStateFlow()

    // Number of the folder to open. The screen navigates when this is not null.
    private val _folderToOpen = MutableStateFlow<String?>(null)
    val folderToOpen: StateFlow<String?> = _folderToOpen.asStateFlow()

    fun onNumberChange(value: String) {
        _number.value = value.filter { it.isDigit() }
    }

    fun onContinue() {
        val number = _number.value
        if (number.isBlank()) return

        if (repository.getFolder(number) != null) {
            _showExistsDialog.value = true
        } else {
            _showCreateDialog.value = true
        }
    }

    fun onConfirmCreate() {
        val number = _number.value
        _showCreateDialog.value = false
        repository.createFolder(number)
        _folderToOpen.value = number
    }

    fun onDismissCreateDialog() {
        _showCreateDialog.value = false
    }

    fun onOpenExistingFolder() {
        _showExistsDialog.value = false
        _folderToOpen.value = _number.value
    }

    fun onDismissExistsDialog() {
        _showExistsDialog.value = false
    }

    fun onFolderOpened() {
        _folderToOpen.value = null
    }
}