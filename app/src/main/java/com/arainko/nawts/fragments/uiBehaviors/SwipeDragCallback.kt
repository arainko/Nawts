package com.arainko.nawts.fragments.uiBehaviors

import android.util.Log
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.arainko.nawts.extensions.makeToast
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

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        val adapter = recyclerView.adapter as NoteAdapter

        val fromPos = viewHolder.adapterPosition
        val toPos = target.adapterPosition

        if (fromPos < toPos) {
            for (i in fromPos downTo toPos) {
                val order1 = adapter.currentList[i].order
                val order2 = adapter.currentList[i+1].order
                adapter.currentList[i].order = order2
                adapter.currentList[i+1].order = order1
            }
        } else {
            for (i in fromPos .. toPos) {
                val order1 = adapter.currentList[i].order
                val order2 = adapter.currentList[i-1].order
                adapter.currentList[i].order = order2
                adapter.currentList[i-1].order = order1
            }
        }
        adapter.notifyItemMoved(fromPos, toPos)
        return false
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        super.onSelectedChanged(viewHolder, actionState)
        when(actionState) {
            ItemTouchHelper.ACTION_STATE_IDLE -> {
                    fragment.noteAdapter.currentList.forEach {
                        Log.d("UPDATING", it.toString())
                        modelActions.updateNote(it)
                    }
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
            "Pos ${note.order}".makeToast(fragment.context, false)
//            val bottomSheet = BottomSheetCustomizerFragment(modelActions, note, fragment.noteAdapter, viewHolder.adapterPosition)
//            bottomSheet.show(fragment.activity!!.supportFragmentManager, "COS")
            fragment.noteAdapter.notifyItemChanged(viewHolder.adapterPosition)
        }
}