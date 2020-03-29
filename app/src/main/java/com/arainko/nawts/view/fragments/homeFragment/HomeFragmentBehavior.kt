package com.arainko.nawts.view.fragments.homeFragment

import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.arainko.nawts.R
import com.arainko.nawts.view.fragments.customizationFragment.CustomizerFragment
import com.arainko.nawts.view.abstracts.FragmentUIBehavior
import com.arainko.nawts.view.abstracts.HolderBehavior
import com.arainko.nawts.view.abstracts.StartDragListener
import com.arainko.nawts.persistence.entities.Note
import com.arainko.nawts.view.viewmodels.NoteViewModel
import com.arainko.nawts.view.elements.NoteHolder
import kotlinx.android.synthetic.main.fragment_home.*


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
        fragment.fab.hide()
//        fragment.homeBottomAppBar.performHide()
        findNavController(fragment).navigate(action, extras)
    }

    val recyclerViewSwipeToDismissListener: ItemTouchHelper =
        SwipeDragCallback(fragment, model).callBack

    val bottomBarListener = Toolbar.OnMenuItemClickListener {
        when (it.itemId) {
            R.id.searchMenuItem -> {
               fragment.searchView.visibility = View.VISIBLE
                fragment.searchView.setIconifiedByDefault(false)
                fragment.searchView.requestFocus()
                true
            }
            else -> false
        }
    }

    override fun onHolderClick(holder: NoteHolder) {
        val extras = FragmentNavigatorExtras(
            holder.itemView to holder.itemView.transitionName
        )
        val action = HomeFragmentDirections
            .actionToEditingFragment(holder.note, model.maxOrder, holder.note.transitionName)
        fragment.fab.hide()
        fragment.homeBottomAppBar.performHide()
        findNavController(fragment).navigate(action, extras)
    }

    override fun onHolderLongClick(holder: NoteHolder): Boolean {
        val bottomSheet = CustomizerFragment(model, holder, fragment.noteAdapter)
        bottomSheet.show(fragment.activity!!.supportFragmentManager, "COS")
        return true
    }

    override fun requestDrag(viewHolder: RecyclerView.ViewHolder) {
        recyclerViewSwipeToDismissListener.startDrag(viewHolder)
    }

}