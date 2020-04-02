package com.arainko.nawts.view.elements

import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.findFragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.arainko.nawts.R
import com.arainko.nawts.view.abstracts.HolderBehavior
import com.arainko.nawts.view.abstracts.StartDragListener
import com.arainko.nawts.persistence.entities.Note
import kotlinx.android.synthetic.main.note_layout.view.*

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
                dragListener.requestDrag(this@NoteHolder)
            false
        }

    }
}