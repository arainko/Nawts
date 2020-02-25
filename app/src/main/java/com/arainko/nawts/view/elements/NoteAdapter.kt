package com.arainko.nawts.view.elements

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.arainko.nawts.R
import com.arainko.nawts.asIntColor
import com.arainko.nawts.view.abstracts.HolderBehavior
import com.arainko.nawts.view.abstracts.StartDragListener
import com.arainko.nawts.persistence.entities.Note
import com.google.android.material.card.MaterialCardView

class NoteAdapter(private val holderBehavior: HolderBehavior<Note>, private val dragListner: StartDragListener) : ListAdapter<Note, NoteHolder>(
    NoteDiffUtil
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_layout, parent, false)
        return NoteHolder(
            itemView,
            holderBehavior,
            dragListner
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

    override fun onCurrentListChanged(
        previousList: MutableList<Note>,
        currentList: MutableList<Note>
    ) {
//        super.onCurrentListChanged(previousList, currentList)
//        currentList.sortByDescending { it.order }
    }

    fun noteAt(position: Int): Note = getItem(position)
}