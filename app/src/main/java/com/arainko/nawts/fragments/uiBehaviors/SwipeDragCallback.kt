package com.arainko.nawts.fragments.uiBehaviors

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.arainko.nawts.extensions.makeToast
import com.arainko.nawts.fragments.BottomSheetCustomizerFragment
import com.arainko.nawts.fragments.HomeFragment
import com.arainko.nawts.persistence.viewmodel.ModelActions
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

        val noteTarget = fragment.noteAdapter.noteAt(targetPos)
        val noteDragged = fragment.noteAdapter.noteAt(draggedPos)

        val temp = noteDragged.id
        noteDragged.id = noteTarget.id
        noteTarget.id = temp

        if (dragFrom == -1) {
            dragFrom = draggedPos
        }
        dragTo = targetPos

        fragment.noteAdapter.notifyItemMoved(draggedPos, targetPos)
        return false
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        super.onSelectedChanged(viewHolder, actionState)

        when(actionState) {
            ItemTouchHelper.ACTION_STATE_IDLE -> {
                if(dragFrom != -1 && dragTo != -1 && dragFrom != dragTo) {
                    "From $dragFrom to $dragTo".makeToast(fragment.context)
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
            val note = fragment.noteAdapter.noteAt(viewHolder.adapterPosition)
            val bottomSheet = BottomSheetCustomizerFragment(modelActions, note, fragment.noteAdapter, viewHolder.adapterPosition)
            bottomSheet.show(fragment.activity!!.supportFragmentManager, "COS")
            fragment.noteAdapter.notifyItemChanged(viewHolder.adapterPosition)
        }
}