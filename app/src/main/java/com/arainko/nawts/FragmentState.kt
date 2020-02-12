package com.arainko.nawts

import android.animation.ValueAnimator
import android.view.View
import com.arainko.nawts.fragments.MainFragment
import com.arainko.nawts.model.NoteViewModel
import com.arainko.nawts.persistence.Note
import com.arainko.nawts.view.HolderBehavior

typealias FabOnClickListener = View.OnClickListener

abstract class FragmentState(protected val fragment: MainFragment, protected val model: NoteViewModel) :
    HolderBehavior<Note>, FabOnClickListener {
    abstract fun onStateEnter()
    abstract fun onStateExit()
}