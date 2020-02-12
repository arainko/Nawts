package com.arainko.nawts.view

import android.view.View
import androidx.lifecycle.Observer
import com.arainko.nawts.FragmentState
import com.arainko.nawts.fragments.MainFragment
import com.arainko.nawts.persistence.NoteViewModel
import com.arainko.nawts.persistence.Note
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.fragment_main.*

class CustomizationFragmentState(fragment: MainFragment, model: NoteViewModel) :
    FragmentState(fragment, model) {

    override fun onStateEnter() {
        fragment.fab.hide()
        val newadapter: NoteAdapter = NoteAdapter(this)
        model.notes.observe(fragment, Observer { newadapter.submitList(it.reversed()) })
        fragment.recyclerView.swapAdapter(newadapter, false)
    }

    override fun onStateExit() {
        fragment.fab.show()
    }

    override fun onHolderClick(holderItem: Note, view: View) {
        fragment.sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    override fun onHolderLongClick(holderItem: Note, view: View): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}