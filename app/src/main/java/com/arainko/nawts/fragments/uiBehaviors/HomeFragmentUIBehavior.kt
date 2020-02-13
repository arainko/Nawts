package com.arainko.nawts.fragments.uiBehaviors

import android.view.View
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.arainko.nawts.fragments.HomeFragment
import com.arainko.nawts.fragments.HomeFragmentDirections
import com.arainko.nawts.fragments.uiBehaviors.abstracts.FragmentUIBehavior
import com.arainko.nawts.fragments.uiBehaviors.abstracts.HolderBehavior
import com.arainko.nawts.persistence.NoteViewModel
import com.arainko.nawts.persistence.Note
import kotlinx.android.synthetic.main.note_layout.view.*

class HomeFragmentUIBehavior(fragment: HomeFragment, private val model: NoteViewModel) :
    FragmentUIBehavior<HomeFragment>(fragment),
    HolderBehavior<Note>, View.OnClickListener {

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
        model.deleteAction(id = holderItem.id)
        return false
    }
}