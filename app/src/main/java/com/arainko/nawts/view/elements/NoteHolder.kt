package com.arainko.nawts.view.elements

import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arainko.nawts.view.abstracts.HolderBehavior
import com.arainko.nawts.persistence.entities.Note
import kotlinx.android.synthetic.main.note_layout.view.*

typealias StartDragListener = (RecyclerView.ViewHolder) -> Unit

class NoteHolder(itemView: View, behavior: HolderBehavior<NoteHolder>, dragListener: StartDragListener) : RecyclerView.ViewHolder(itemView) {

    lateinit var note: Note
    val uiNoteContent: TextView = itemView.cardText
    val uiNoteHeader: TextView = itemView.cardHeader

    init {
        itemView.apply {
        setOnClickListener { behavior.onHolderClick(this@NoteHolder) }
        setOnLongClickListener { behavior.onHolderLongClick(this@NoteHolder) }
        }

        itemView.iconButton.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN)
                dragListener(this)
            false
        }

    }
}