package com.arainko.nawts.view

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arainko.nawts.persistence.Note
import kotlinx.android.synthetic.main.note_layout.view.*

class NoteHolder(itemView: View, private val behavior: HolderBehavior<Note>) : RecyclerView.ViewHolder(itemView) {

    lateinit var note: Note
    val noteContent: TextView = itemView.cardText
    val noteHeader: TextView = itemView.cardHeader

    init {
        itemView.apply {
        setOnClickListener { behavior.onHolderClick(note, this) }
        setOnLongClickListener { behavior.onHolderLongClick(note, this) }
        }
    }
}