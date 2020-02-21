package com.arainko.nawts.fragments.uiBehaviors

import android.view.View
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.arainko.nawts.fragments.BottomSheetCustomizerFragment
import com.arainko.nawts.fragments.HomeFragment
import com.arainko.nawts.fragments.HomeFragmentDirections
import com.arainko.nawts.fragments.uiBehaviors.abstracts.FragmentUIBehavior
import com.arainko.nawts.fragments.uiBehaviors.abstracts.HolderBehavior
import com.arainko.nawts.persistence.entities.Note
import com.arainko.nawts.persistence.viewmodel.ModelActions
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragmentBehavior(fragment: HomeFragment, private val modelActions: ModelActions) :
    FragmentUIBehavior<HomeFragment>(fragment),
    HolderBehavior<Note> {

    val fabOnClickListener = View.OnClickListener {
        findNavController(fragment).navigate(
            HomeFragmentDirections.actionToEditingFragment(Note("", ""))
        )
    }

    val recyclerViewSwipeToDismissListener: ItemTouchHelper
        get() {
            val recyclerViewSwipeCallback =
                object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                    override fun onMove(
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder
                    ): Boolean = false

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
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
            return ItemTouchHelper(recyclerViewSwipeCallback)
        }

    override fun onHolderClick(holderItem: Note, view: View, position: Int) = Navigation.findNavController(view).navigate(
        HomeFragmentDirections.actionToEditingFragment(holderItem)
    )


    override fun onHolderLongClick(holderItem: Note, view: View, position: Int): Boolean {
        val bottomSheet = BottomSheetCustomizerFragment(modelActions, holderItem, fragment.noteAdapter, position)
        bottomSheet.show(fragment.activity!!.supportFragmentManager, "COS")
        return true
    }


}