package com.example.evides.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.evides.R
import com.example.evides.domain.model.Evidence

class EvidenceCrudAdapter(private var evidences: List<Evidence>,
                          val itemClickListener: OnEvidenceClickListener) :
    RecyclerView.Adapter<EvidenceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EvidenceViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_crud_evidence, parent, false)
        return EvidenceViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EvidenceViewHolder, position: Int) {
        val item = evidences[position]
        holder.bind(item, itemClickListener)
    }

    override fun getItemCount(): Int {
        return evidences.size
    }

    fun setData(newEvidences: List<Evidence>) {
        evidences = newEvidences
        notifyDataSetChanged()
    }
}

class EvidenceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvNote = view.findViewById<TextView>(R.id.txtNote_Item)
    val tvDetail = view.findViewById<TextView>(R.id.txtDetail_Item)

    fun bind(evidence: Evidence, itemClickListener: OnEvidenceClickListener) {
        tvNote.text = evidence.note
        tvDetail.text = itemView.context.getString(R.string.crud_item_detail, evidence.id)
        itemView.setOnClickListener {
            itemClickListener.onEvidenceClick(evidence)
        }
    }
}