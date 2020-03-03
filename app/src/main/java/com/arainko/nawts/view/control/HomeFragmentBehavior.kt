package com.arainko.nawts.view.control

import android.view.View
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.arainko.nawts.view.containters.BottomSheetCustomizerFragment
import com.arainko.nawts.view.containters.HomeFragment
import com.arainko.nawts.view.abstracts.FragmentUIBehavior
import com.arainko.nawts.view.abstracts.HolderBehavior
import com.arainko.nawts.view.abstracts.StartDragListener
import com.arainko.nawts.persistence.entities.Note
import com.arainko.nawts.view.containters.HomeFragmentDirections


class HomeFragmentBehavior(fragment: HomeFragment, private val modelActions: NoteViewModel) :
    FragmentUIBehavior<HomeFragment>(fragment),
    HolderBehavior<Note>,
    StartDragListener {

    val fabOnClickListener = View.OnClickListener {
        findNavController(fragment).navigate(
            HomeFragmentDirections.actionToEditingFragment(Note("", ""), modelActions.getMaxOrder())
        )
    }

    val recyclerViewSwipeToDismissListener: ItemTouchHelper =
        SwipeDragCallback(fragment, modelActions).callBack


    override fun onHolderClick(holderItem: Note, view: View, position: Int) =
        Navigation.findNavController(view).navigate(
            HomeFragmentDirections.actionToEditingFragment(holderItem, modelActions.getMaxOrder())
        )

    override fun onHolderLongClick(holderItem: Note, view: View, position: Int): Boolean {
        val bottomSheet =
            BottomSheetCustomizerFragment(
                modelActions,
                holderItem,
                fragment.noteAdapter,
                position
            )
        bottomSheet.show(fragment.activity!!.supportFragmentManager, "COS")
        return true
    }

    override fun requestDrag(viewHolder: RecyclerView.ViewHolder) {
        recyclerViewSwipeToDismissListener.startDrag(viewHolder)
    }


}