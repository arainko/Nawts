package com.arainko.nawts.fragments.uiBehaviors

import android.view.View
import com.arainko.nawts.fragments.BottomSheetCustomizerFragment
import com.arainko.nawts.fragments.uiBehaviors.abstracts.FragmentUIBehavior
import com.arainko.nawts.persistence.entities.Note
import com.arainko.nawts.persistence.viewmodel.ModelActions
import com.arainko.nawts.view.NoteAdapter
import java.text.FieldPosition

class CustomizerFragmentBehavior(fragment: BottomSheetCustomizerFragment,
                                 val modelActions: ModelActions,
                                 val note: Note,
                                 val adapter: NoteAdapter,
                                 val adapterPosition: Int) :
    FragmentUIBehavior<BottomSheetCustomizerFragment>(fragment) {

    private var currentBackgroundColor: String = note.style.backgroundColor
    private var currentStrokeColor: String = note.style.strokeColor

    val onColorButtonClickListener = View.OnClickListener {

    }

}