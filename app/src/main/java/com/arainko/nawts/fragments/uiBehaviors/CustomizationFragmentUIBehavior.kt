package com.arainko.nawts.fragments.uiBehaviors

import android.view.View
import com.arainko.nawts.fragments.CustomizationFragment
import com.arainko.nawts.fragments.uiBehaviors.abstracts.FragmentUIBehavior
import com.arainko.nawts.fragments.uiBehaviors.abstracts.HolderBehavior
import com.arainko.nawts.persistence.Note
import com.arainko.nawts.persistence.NoteViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior

class CustomizationFragmentUIBehavior(
    fragment: CustomizationFragment,
    private val model: NoteViewModel
) : FragmentUIBehavior<CustomizationFragment>(fragment),
    HolderBehavior<Note> {

    override fun onHolderClick(holderItem: Note, view: View) {
        fragment.sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    override fun onHolderLongClick(holderItem: Note, view: View): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}