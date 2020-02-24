package com.arainko.nawts.fragments.uiBehaviors

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_DRAG
import androidx.recyclerview.widget.RecyclerView
import com.arainko.nawts.extensions.makeToast
import com.arainko.nawts.fragments.HomeFragment
import com.arainko.nawts.persistence.viewmodel.NoteViewModel
import com.arainko.nawts.view.NoteHolder
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_home.*

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
        if (fromPosCache == -1) fromPosCache = fromPos

        toPosCache = toPos

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
        val currentNotes = fragment.noteAdapter.currentList.sortedByDescending { it.order }
        if (fromPosCache != -1 && fromPosCache != toPosCache) {
            currentNotes[fromPosCache].order = currentNotes[toPosCache].order

            if (fromPosCache > toPosCache) {
                for (i: Int in fromPosCache+1..toPosCache) {
                    currentNotes[i].order -= 1
                }
            } else if (fromPosCache < toPosCache) {
                for (i: Int in fromPosCache-1 downTo toPosCache) {
                    currentNotes[i].order += 1
                }
            }
            model.updateNotes(currentNotes)
        }
        fromPosCache = -1
        toPosCache = -1
    }

}