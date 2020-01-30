package com.arainko.nawts.view

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.arainko.nawts.R
import com.arainko.nawts.model.NoteViewModel
import com.arainko.nawts.persistence.Note
import kotlinx.android.synthetic.main.note_layout.view.*
import kotlin.properties.Delegates

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    var notes: List<Note> = ArrayList()
    set(value) {
        field = value
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentNote = notes[position]
        holder.noteContent.text = currentNote.content
        holder.noteHeader.text = currentNote.header
        holder.noteId = currentNote.id
    }

    override fun getItemCount(): Int = notes.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener {
        var noteId: Int by Delegates.notNull()
        val noteContent: TextView = itemView.cardText
        val noteHeader: TextView = itemView.cardHeader
        init { itemView.setOnClickListener(this) }

        override fun onClick(view: View) {
            val action = MainFragmentDirections.actionMainFragmentToNoteEditFragment(
                noteHeader.text.toString(),
                noteContent.text.toString(),
                noteId)
            Navigation.findNavController(view).navigate(action)
        }

        override fun onLongClick(view: View?): Boolean = TODO()

    }

}