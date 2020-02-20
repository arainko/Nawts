package com.arainko.nawts.view

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arainko.nawts.fragments.uiBehaviors.abstracts.HolderBehavior
import com.arainko.nawts.persistence.entities.Note
import kotlinx.android.synthetic.main.note_layout.view.*

class NoteHolder(itemView: View, behavior: HolderBehavior<Note>) : RecyclerView.ViewHolder(itemView) {

    lateinit var note: Note
    val uiNoteContent: TextView = itemView.cardText
    val uiNoteHeader: TextView = itemView.cardHeader

    init {
        itemView.apply {
        setOnClickListener { behavior.onHolderClick(note, this, adapterPosition) }
        setOnLongClickListener { behavior.onHolderLongClick(note, this, adapterPosition) }
        }
    }
}