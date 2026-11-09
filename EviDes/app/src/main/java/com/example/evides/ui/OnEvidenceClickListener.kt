package com.example.evides.ui

import com.example.evides.domain.model.Evidence

interface OnEvidenceClickListener {
    fun onEvidenceClick(evidence: Evidence)
}