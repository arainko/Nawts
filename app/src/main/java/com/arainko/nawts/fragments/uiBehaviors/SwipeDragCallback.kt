package com.arainko.nawts.fragments.uiBehaviors

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_DRAG
import androidx.recyclerview.widget.RecyclerView
import com.arainko.nawts.extensions.makeToast
import com.arainko.nawts.fragments.HomeFragment
import com.arainko.nawts.persistence.viewmodel.NoteViewModel
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
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        if (fromPosCache == -1) fromPosCache = viewHolder.adapterPosition
        toPosCache = target.adapterPosition
        recyclerView.adapter?.notifyItemMoved(viewHolder.adapterPosition, toPosCache)
        return false
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
        "From $fromPosCache to $toPosCache".makeToast(fragment.context, false)
//        fragment.noteAdapter.noteAt(fromPosCache).order = toPosCache
        if (fromPosCache != toPosCache)
            model.updateNoteOrder(fragment.noteAdapter ,fromPosCache, toPosCache)
        fromPosCache = -1
        toPosCache = -1
    }

}