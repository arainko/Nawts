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
    ItemTouchHelper.UP or ItemTouchHelper.DOWN,
    ItemTouchHelper.RIGHT) {

    val callBack = ItemTouchHelper(this)

    private var fromPosCache: Int = -1
    private var toPosCache: Int = -1

    override fun isLongPressDragEnabled(): Boolean = false

    override fun onMove(
        recyclerView: RecyclerView,
        draggedHolder: RecyclerView.ViewHolder,
        targetHolder: RecyclerView.ViewHolder
    ): Boolean {
//        val draggedNote = (draggedHolder as NoteHolder).note
//        val targetNote = (targetHolder as NoteHolder).note

//        val tempOrder = draggedNote.order
//        draggedNote.order = targetNote.order
//        targetNote.order = tempOrder
//        model.updateNotes(listOf(draggedNote, targetNote))
        return true
    }

    override fun onMoved(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        fromPos: Int,
        target: RecyclerView.ViewHolder,
        toPos: Int,
        x: Int,
        y: Int
    ) {
        (viewHolder as NoteHolder).note.order = (target as NoteHolder).note.order
        target.note.order -= 1
        Log.d("ONMOVED", "From $fromPos to $toPos")
        Log.d("ONMOVED", fragment.noteAdapter.currentList.sortedByDescending { it.order }.map { it.header }.toString())

        recyclerView.adapter?.notifyItemMoved(fromPos, toPos)
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int)  {
        val position = viewHolder.adapterPosition
        val note = fragment.noteAdapter.noteAt(position)
        model.deleteNote(note)
        Snackbar.make(fragment.layoutContainer, "Deleted", Snackbar.LENGTH_LONG).apply {
            animationMode = Snackbar.ANIMATION_MODE_FADE
            setAction("Undo") { model.addNote(note) }
        }.show()
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        super.onSelectedChanged(viewHolder, actionState)
        if (actionState == ACTION_STATE_DRAG) viewHolder?.itemView?.alpha = 0.5f
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        viewHolder.itemView.alpha = 1f
        val list = fragment.noteAdapter
            .currentList
            .sortedByDescending { it.order }
            .mapIndexed { index, note -> note.apply { order = index } }

        Log.d("UPDATING WITH", list.map { "HEADER: ${it.header} ORDER: ${it.order}" }.toString())

        model.updateNote(*list.toTypedArray())

        model.launch {
            delay(1000)
            Log.d("AFTER UPDATE", fragment
                .noteAdapter
                .currentList
                .sortedByDescending { it.order }
                .map { "HEADER: ${it.header} ORDER: ${it.order}" }
                .toString())
            delay(1000)
            repeat(list.size) {
                fragment.noteAdapter.notifyItemChanged(it-1)
            }
        }



        fromPosCache = -1
        toPosCache = -1
    }

}