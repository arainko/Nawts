package com.arainko.nawts.fragments.uiBehaviors

import android.view.View
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
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
    HolderBehavior<Note>,
    View.OnClickListener {

    override fun onClick(v: View?) = findNavController(fragment).navigate(
        HomeFragmentDirections.actionMainFragmentToNoteEditFragment("", "")
    )

    override fun onHolderClick(holderItem: Note, view: View) {
        val action =
            HomeFragmentDirections.actionMainFragmentToNoteEditFragment(
                view.cardHeader.text.toString(),
                view.cardText.text.toString(),
                holderItem.id
            )
        Navigation.findNavController(view).navigate(action)
    }

    override fun onHolderLongClick(holderItem: Note, view: View): Boolean {
        fragment.sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        return true
    }

    private val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = false

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            val note = fragment.noteAdapter.noteAt(position)
            model.deleteAction(id = note.id)
            Snackbar.make(fragment.layoutContainer, "Deleted", Snackbar.LENGTH_LONG).apply {
                animationMode = Snackbar.ANIMATION_MODE_FADE
                setAction("Undo") { model.addNote(note.header, note.content) }
            }.show()
        }
    }

    val swiper = ItemTouchHelper(itemTouchHelper)

}