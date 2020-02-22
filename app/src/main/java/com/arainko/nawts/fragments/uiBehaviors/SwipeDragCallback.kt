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
    0,
    ItemTouchHelper.RIGHT) {

    val callBack = ItemTouchHelper(this)

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int)  {
        val position = viewHolder.adapterPosition
        val note = fragment.noteAdapter.noteAt(position)
        modelActions.deleteNote(note)
        Snackbar.make(fragment.layoutContainer, "Deleted", Snackbar.LENGTH_LONG)
        .apply {
            animationMode = Snackbar.ANIMATION_MODE_FADE
            setAction("Undo") { modelActions.addNote(note) }
        }.show()
}

}