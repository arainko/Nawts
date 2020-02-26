package com.arainko.nawts.view.control

import android.util.Log
import androidx.lifecycle.Transformations.map
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_DRAG
import androidx.recyclerview.widget.RecyclerView
import com.arainko.nawts.view.containters.HomeFragment
import com.arainko.nawts.view.elements.NoteHolder
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SwipeDragCallback(val fragment: HomeFragment, val model: NoteViewModel) : ItemTouchHelper.SimpleCallback(
    0,
    ItemTouchHelper.RIGHT) {

    val callBack = ItemTouchHelper(this)

    override fun isLongPressDragEnabled(): Boolean = false

    override fun onMove(
        recyclerView: RecyclerView,
        draggedHolder: RecyclerView.ViewHolder,
        targetHolder: RecyclerView.ViewHolder
    ): Boolean = false


    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int)  {
        val position = viewHolder.adapterPosition
        val note = fragment.noteAdapter.noteAt(position)
        model.deleteNote(note)
        Snackbar.make(fragment.layoutContainer, "Deleted", Snackbar.LENGTH_LONG).apply {
            animationMode = Snackbar.ANIMATION_MODE_FADE
            setAction("Undo") { model.addNote(note) }
        }.show()
    }

}