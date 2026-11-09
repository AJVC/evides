package com.example.evides.ui

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.evides.R
import com.example.evides.domain.model.Evidence
import com.example.evides.domain.model.EvidenceType
import com.example.evides.viewmodel.EvidenceCrudViewModel

class EvidenceCrudActivity : AppCompatActivity(), OnEvidenceClickListener {

    private lateinit var txtSearch: EditText
    private lateinit var txtNote: EditText
    private lateinit var buttonDelete: ImageButton
    private lateinit var adapter: EvidenceCrudAdapter
    private var isEditMode: Boolean = false
    private var selectedId: Long = -1
    private var folderCi: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_evidence_crud)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        txtSearch = findViewById<EditText>(R.id.txtSearch_Evidence)
        txtNote = findViewById<EditText>(R.id.txtNote_Evidence)
        buttonDelete = findViewById<ImageButton>(R.id.buttonDelete)

        val textTitle = findViewById<TextView>(R.id.textCrudTitle)
        val btnSearch = findViewById<ImageButton>(R.id.btnSearch_Evidence)
        val buttonBack = findViewById<ImageButton>(R.id.buttonBack)
        val buttonSave = findViewById<ImageButton>(R.id.buttonSave)
        val buttonCancel = findViewById<ImageButton>(R.id.buttonCancel)
        val recycler = findViewById<RecyclerView>(R.id.rvEvidences_Crud)

        folderCi = intent.getStringExtra(EXTRA_CI_NUMBER) ?: ""
        textTitle.text = "CI-" + folderCi

        adapter = EvidenceCrudAdapter(emptyList(), this)
        recycler.layoutManager = LinearLayoutManager(applicationContext)
        recycler.adapter = adapter
        loadList()

        btnSearch.setOnClickListener(View.OnClickListener { view ->
            searchByNote()
        })

        buttonBack.setOnClickListener(View.OnClickListener { view ->
            finish()
        })

        buttonSave.setOnClickListener(View.OnClickListener { view ->
            saveEvidence()
        })

        buttonDelete.setOnClickListener(View.OnClickListener { view ->
            askDeleteEvidence()
        })

        buttonCancel.setOnClickListener(View.OnClickListener { view ->
            cleanScreen()
        })
    }

    override fun onEvidenceClick(evidence: Evidence) {
        isEditMode = true
        selectedId = evidence.id
        txtNote.setText(evidence.note)
        buttonDelete.visibility = View.VISIBLE
    }

    private fun loadList() {
        adapter.setData(EvidenceCrudViewModel.getEvidencesByCi(folderCi))
    }

    private fun searchByNote() {
        val query = txtSearch.text.toString().trim()
        val all = EvidenceCrudViewModel.getEvidencesByCi(folderCi)
        if (query.isBlank()) {
            adapter.setData(all)
            return
        }
        val filtered = all.filter { it.note.contains(query, ignoreCase = true) }
        adapter.setData(filtered)
        if (filtered.isEmpty()) {
            Toast.makeText(this, getString(R.string.crud_msg_not_found),
                Toast.LENGTH_LONG).show()
        }
    }

    private fun cleanScreen() {
        isEditMode = false
        selectedId = -1
        txtSearch.setText("")
        txtNote.setText("")
        buttonDelete.visibility = View.GONE
        loadList()
    }

    private fun isValidationData(): Boolean {
        return txtNote.text.toString().trim().isNotBlank()
    }

    private fun saveEvidence() {
        try {
            if (!isValidationData()) {
                Toast.makeText(this, getString(R.string.crud_msg_incomplete),
                    Toast.LENGTH_LONG).show()
                return
            }
            if (!isEditMode) {
                val evidence = Evidence(
                    0,
                    folderCi,
                    EvidenceType.PHOTO,
                    "",
                    null,
                    null,
                    System.currentTimeMillis(),
                    txtNote.text.toString().trim()
                )
                EvidenceCrudViewModel.saveEvidence(evidence)
                cleanScreen()
                Toast.makeText(this, getString(R.string.crud_msg_saved),
                    Toast.LENGTH_LONG).show()
            } else {
                askUpdateEvidence()
            }
        } catch (e: Exception) {
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_LONG).show()
        }
    }

    private fun askUpdateEvidence() {
        AlertDialog.Builder(this)
            .setMessage(getString(R.string.crud_dialog_update_question))
            .setPositiveButton(getString(R.string.crud_yes)) { dialog, which ->
                updateEvidence()
            }
            .setNegativeButton(getString(R.string.crud_no), null)
            .show()
    }

    private fun updateEvidence() {
        try {
            val current = EvidenceCrudViewModel.getEvidenceById(selectedId)
            if (current != null) {
                val changed = current.copy(note = txtNote.text.toString().trim())
                EvidenceCrudViewModel.updateEvidence(changed)
                cleanScreen()
                Toast.makeText(this, getString(R.string.crud_msg_updated),
                    Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, getString(R.string.crud_msg_not_found),
                    Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_LONG).show()
        }
    }

    private fun askDeleteEvidence() {
        AlertDialog.Builder(this)
            .setMessage(getString(R.string.crud_dialog_delete_question))
            .setPositiveButton(getString(R.string.crud_yes)) { dialog, which ->
                deleteEvidence()
            }
            .setNegativeButton(getString(R.string.crud_no), null)
            .show()
    }

    private fun deleteEvidence() {
        try {
            EvidenceCrudViewModel.removeEvidence(selectedId)
            cleanScreen()
            Toast.makeText(this, getString(R.string.crud_msg_deleted),
                Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_LONG).show()
        }
    }
}