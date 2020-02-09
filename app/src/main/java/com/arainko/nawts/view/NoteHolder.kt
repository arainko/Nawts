package com.arainko.nawts.view

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.arainko.nawts.fragments.MainFragmentDirections
import com.arainko.nawts.model.DatabaseActions
import com.arainko.nawts.persistence.Note
import kotlinx.android.synthetic.main.note_layout.view.*
import kotlin.properties.Delegates

class NoteHolder(itemView: View, val behavior: NoteHolderBehavior) : RecyclerView.ViewHolder(itemView) {

    lateinit var note: Note
    val noteContent: TextView = itemView.cardText
    val noteHeader: TextView = itemView.cardHeader

    init {
        itemView.apply {
        setOnClickListener { behavior.onClick(note, this) }
        setOnLongClickListener { behavior.onLongClick(note, this) }
        }
    }
}