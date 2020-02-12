package com.arainko.nawts.view

import android.animation.ValueAnimator
import android.view.View
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.arainko.nawts.FragmentState
import com.arainko.nawts.extensions.asColor
import com.arainko.nawts.fragments.MainFragment
import com.arainko.nawts.fragments.MainFragmentDirections
import com.arainko.nawts.persistence.NoteViewModel
import com.arainko.nawts.persistence.Note
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.note_layout.view.*

class DefaultFragmentState(fragment: MainFragment, model: NoteViewModel) :
    FragmentState(fragment, model) {

    override fun onStateEnter() {
        val colorAnimation: ValueAnimator =
            ValueAnimator.ofArgb("#ffffff".asColor(), "#000000".asColor())
                .apply {
                    duration = 3000
                    addUpdateListener { animator ->
                        fragment.layoutContainer.setBackgroundColor(animator.animatedValue as Int)
                    }
                }
//        colorAnimation.start()
    }

    override fun onStateExit() { }

    override fun onClick(v: View?) = findNavController(fragment).navigate(
        MainFragmentDirections.actionMainFragmentToNoteEditFragment("", "")
    )

    // RecyclerView's ViewHolder onClickListener
    override fun onHolderClick(holderItem: Note, view: View) {
        val action =
            MainFragmentDirections.actionMainFragmentToNoteEditFragment(
                view.cardHeader.text.toString(),
                view.cardText.text.toString(),
                holderItem.id
            )
        Navigation.findNavController(view).navigate(action)
    }

    // RecyclerView's ViewHolder onLongClickListener
    override fun onHolderLongClick(holderItem: Note, view: View): Boolean {
        model.deleteAction(id = holderItem.id)
        return false
    }
}