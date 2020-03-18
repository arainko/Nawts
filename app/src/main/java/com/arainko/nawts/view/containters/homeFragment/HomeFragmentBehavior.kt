package com.arainko.nawts.view.containters.homeFragment

import android.view.View
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.arainko.nawts.view.containters.customizationFragment.BottomSheetCustomizerFragment
import com.arainko.nawts.view.abstracts.FragmentUIBehavior
import com.arainko.nawts.view.abstracts.HolderBehavior
import com.arainko.nawts.view.abstracts.StartDragListener
import com.arainko.nawts.persistence.entities.Note
import com.arainko.nawts.view.NoteViewModel
import com.arainko.nawts.view.elements.NoteHolder
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.note_layout.view.*


class HomeFragmentBehavior(fragment: HomeFragment, private val model: NoteViewModel) :
    FragmentUIBehavior<HomeFragment>(fragment),
    HolderBehavior<NoteHolder>,
    StartDragListener {

    val fabOnClickListener = View.OnClickListener {
        val emptyNote = Note("", "")
        val extras = FragmentNavigatorExtras(
            fragment.fab to fragment.fab.transitionName
        )
        val action = HomeFragmentDirections
            .actionToEditingFragment(emptyNote, model.maxOrder, fragment.fab.transitionName)
        findNavController(fragment).navigate(action, extras)
    }

    val recyclerViewSwipeToDismissListener: ItemTouchHelper =
        SwipeDragCallback(fragment, model).callBack


    override fun onHolderClick(holder: NoteHolder) {
        val extras = FragmentNavigatorExtras(
            holder.itemView to holder.itemView.transitionName
        )
        val action = HomeFragmentDirections
            .actionToEditingFragment(holder.note, model.maxOrder, holder.note.transitionName)
        findNavController(fragment).navigate(action, extras)
    }


    override fun onHolderLongClick(holder: NoteHolder): Boolean {
        val bottomSheet = BottomSheetCustomizerFragment(model, holder, fragment.noteAdapter)
        bottomSheet.show(fragment.activity!!.supportFragmentManager, "COS")
        return true
    }

    override fun requestDrag(viewHolder: RecyclerView.ViewHolder) {
        recyclerViewSwipeToDismissListener.startDrag(viewHolder)
    }

}