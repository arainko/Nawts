package com.arainko.nawts.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.arainko.nawts.R
import com.arainko.nawts.model.DatabaseActions
import com.arainko.nawts.persistence.Note

class RecyclerAdapter(private val dbActions: DatabaseActions) : ListAdapter<Note, ViewHolder>(diffUtil) {
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean =
                oldItem.header == newItem.header && oldItem.content == newItem.content && oldItem.id == newItem.id
        }
    }

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