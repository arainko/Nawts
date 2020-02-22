package com.arainko.nawts.fragments.uiBehaviors

import android.util.Log
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.arainko.nawts.extensions.makeToast
import com.arainko.nawts.fragments.BottomSheetCustomizerFragment
import com.arainko.nawts.fragments.HomeFragment
import com.arainko.nawts.persistence.viewmodel.ModelActions
import com.arainko.nawts.view.NoteAdapter
import com.arainko.nawts.view.NoteHolder
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_home.*

class SwipeDragCallback(val fragment: HomeFragment, val modelActions: ModelActions) : ItemTouchHelper.SimpleCallback(
    ItemTouchHelper.UP or ItemTouchHelper.DOWN,
    ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {

    val callBack = ItemTouchHelper(this)

    var dragFrom = -1
    var dragTo = -1


    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        val draggedPos = viewHolder.adapterPosition
        val targetPos = target.adapterPosition
        val dragged = viewHolder as NoteHolder
        val targetTo = target as NoteHolder
//        val temp = noteDragged.id
//        noteDragged.id = noteTarget.id
//        noteTarget.id = temp


        val temp = -1

        if (dragFrom == -1) {
            dragFrom = draggedPos
        }
        dragTo = targetPos
        dragged.note.position = dragTo
        targetTo.note.position = dragFrom

        fragment.noteAdapter.notifyItemMoved(draggedPos, targetPos)
        return false
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        super.onSelectedChanged(viewHolder, actionState)
        when(actionState) {
            ItemTouchHelper.ACTION_STATE_IDLE -> {
                if(dragFrom != -1 && dragTo != -1 && dragFrom != dragTo) {
                    "From $dragFrom to $dragTo".makeToast(fragment.context)
                    fragment.noteAdapter.currentList.forEach {
                        Log.d("UPDATING", it.toString())
                        modelActions.updateNote(it)
                    }
                }
                dragFrom = -1
                dragTo = -1
            }
        }
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) =
        if (direction == ItemTouchHelper.RIGHT) {
            val position = viewHolder.adapterPosition
            val note = fragment.noteAdapter.noteAt(position)
            modelActions.deleteNote(note)
            Snackbar.make(fragment.layoutContainer, "Deleted", Snackbar.LENGTH_LONG)
                .apply {
                    animationMode = Snackbar.ANIMATION_MODE_FADE
                    setAction("Undo") { modelActions.addNote(note) }
                }.show()
        } else {
            val note = (viewHolder as NoteHolder).note
            "Pos ${note.position}".makeToast(fragment.context, false)
//            val bottomSheet = BottomSheetCustomizerFragment(modelActions, note, fragment.noteAdapter, viewHolder.adapterPosition)
//            bottomSheet.show(fragment.activity!!.supportFragmentManager, "COS")
            fragment.noteAdapter.notifyItemChanged(viewHolder.adapterPosition)
        }
}