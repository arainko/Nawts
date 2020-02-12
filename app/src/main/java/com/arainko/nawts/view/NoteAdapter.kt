package com.arainko.nawts.view

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.arainko.nawts.R
import com.arainko.nawts.persistence.Note

class NoteAdapter(private val holderBehavior: HolderBehavior<Note>) : ListAdapter<Note, NoteHolder>(NoteDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_layout, parent, false)
        return NoteHolder(itemView, holderBehavior)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val currentNote = getItem(position)
        holder.note = currentNote
        holder.noteContent.text = currentNote.content
        holder.noteHeader.text = currentNote.header
    }
}