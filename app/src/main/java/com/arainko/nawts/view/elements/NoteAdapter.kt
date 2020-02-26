package com.arainko.nawts.view.elements

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.arainko.nawts.R
import com.arainko.nawts.asIntColor
import com.arainko.nawts.view.abstracts.HolderBehavior
import com.arainko.nawts.view.abstracts.StartDragListener
import com.arainko.nawts.persistence.entities.Note
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.note_layout.view.*

class NoteAdapter(private val holderBehavior: HolderBehavior<Note>, private val dragListener: StartDragListener) : ListAdapter<Note, NoteHolder>(
    NoteDiffUtil
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_layout, parent, false)
            .apply { iconButton.visibility = View.GONE }
        return NoteHolder(
            itemView,
            holderBehavior,
            dragListener
        )
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val currentNote = getItem(position)
        holder.note = currentNote
        holder.uiNoteContent.text = currentNote.content
        holder.uiNoteHeader.text = currentNote.header
        (holder.itemView as MaterialCardView).run {
            setCardBackgroundColor(currentNote.style.backgroundColor.asIntColor())
            strokeColor = currentNote.style.strokeColor.asIntColor()
        }
    }

    fun noteAt(position: Int): Note = getItem(position)
}