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
import com.arainko.nawts.persistence.NoteViewModel
import com.arainko.nawts.persistence.Note
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.note_layout.view.*

class HomeFragmentUIBehavior(fragment: HomeFragment, private val model: NoteViewModel) :
    FragmentUIBehavior<HomeFragment>(fragment),
    HolderBehavior<Note> {

    lateinit var editNote: Note

    val colorOnClickListener = View.OnClickListener {
//        val note = fragment.bottomSheet.tag as Note
//        note.color = it.tag.toString()
//        model.updateNote(note)
    }

    val fabOnClickListener = View.OnClickListener {
        findNavController(fragment).navigate(
            HomeFragmentDirections.actionMainFragmentToNoteEditFragment("", "")
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
                        model.deleteNote(note)
                        Snackbar.make(fragment.layoutContainer, "Deleted", Snackbar.LENGTH_LONG)
                            .apply {
                                animationMode = Snackbar.ANIMATION_MODE_FADE
                                setAction("Undo") { model.addNote(note) }
                            }.show()
                    }
                }
            return ItemTouchHelper(recyclerViewSwipeCallback)
        }

    val bottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onSlide(bottomSheet: View, slideOffset: Float) { }

        override fun onStateChanged(bottomSheet: View, newState: Int) =
            if (newState == BottomSheetBehavior.STATE_HIDDEN) fragment.fab.show() else fragment.fab.hide()

    }

    override fun onHolderClick(holderItem: Note, view: View, position: Int) = Navigation.findNavController(view).navigate(
        HomeFragmentDirections.actionMainFragmentToNoteEditFragment(
            view.cardHeader.text.toString(),
            view.cardText.text.toString(),
            holderItem.id
        )
    )


    override fun onHolderLongClick(holderItem: Note, view: View, position: Int): Boolean {
        val bottomSheet = BottomSheetCustomizerFragment(holderItem, model, fragment.noteAdapter, position)
        bottomSheet.show(fragment.activity!!.supportFragmentManager, "COS")
        return true
    }


}