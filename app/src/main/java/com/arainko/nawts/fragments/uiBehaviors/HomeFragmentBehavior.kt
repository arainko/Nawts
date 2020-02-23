package com.arainko.nawts.fragments.uiBehaviors

import android.graphics.Canvas
import android.view.MotionEvent
import android.view.View
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.arainko.nawts.extensions.makeToast
import com.arainko.nawts.fragments.BottomSheetCustomizerFragment
import com.arainko.nawts.fragments.HomeFragment
import com.arainko.nawts.fragments.HomeFragmentDirections
import com.arainko.nawts.fragments.uiBehaviors.abstracts.FragmentUIBehavior
import com.arainko.nawts.fragments.uiBehaviors.abstracts.HolderBehavior
import com.arainko.nawts.persistence.entities.Note
import com.arainko.nawts.persistence.viewmodel.ModelActions
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_home.*

interface StartDragListener {
    fun requestDrag(viewHolder: RecyclerView.ViewHolder)
}

class HomeFragmentBehavior(fragment: HomeFragment, private val modelActions: ModelActions) :
    FragmentUIBehavior<HomeFragment>(fragment),
    HolderBehavior<Note>,
    StartDragListener {

    val fabOnClickListener = View.OnClickListener {
        findNavController(fragment).navigate(
            HomeFragmentDirections.actionToEditingFragment(Note("", ""), fragment.noteAdapter.itemCount)
        )
    }

    val recyclerViewSwipeToDismissListener: ItemTouchHelper =
        SwipeDragCallback(fragment, modelActions).callBack


    override fun onHolderClick(holderItem: Note, view: View, position: Int) = Navigation.findNavController(view).navigate(
        HomeFragmentDirections.actionToEditingFragment(holderItem, fragment.noteAdapter.itemCount)
    )


    override fun onHolderLongClick(holderItem: Note, view: View, position: Int): Boolean {
        val bottomSheet = BottomSheetCustomizerFragment(modelActions, holderItem, fragment.noteAdapter, position)
        bottomSheet.show(fragment.activity!!.supportFragmentManager, "COS")
        return true
    }

    override fun requestDrag(viewHolder: RecyclerView.ViewHolder) {
        recyclerViewSwipeToDismissListener.startDrag(viewHolder)
    }


}