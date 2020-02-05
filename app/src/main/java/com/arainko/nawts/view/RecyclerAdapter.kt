package com.arainko.nawts.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.arainko.nawts.R
import com.arainko.nawts.model.DatabaseActions
import com.arainko.nawts.persistence.Note

class RecyclerAdapter(private val dbActions: DatabaseActions) : ListAdapter<Note, ViewHolder>(NoteDiffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_layout, parent, false)
        return ViewHolder(itemView, dbActions)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentNote = getItem(position)
        holder.noteContent.text = currentNote.content
        holder.noteHeader.text = currentNote.header
        holder.noteId = currentNote.id
    }
}