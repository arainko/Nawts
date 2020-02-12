package com.arainko.nawts

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.arainko.nawts.fragments.MainFragment
import com.arainko.nawts.model.NoteViewModel
import com.arainko.nawts.view.DefaultFragmentState
import kotlin.properties.Delegates

class StateManager(private val fragment: MainFragment, private val model: NoteViewModel) {
    private val defaultState: FragmentState by lazy { DefaultFragmentState(fragment, model) }
//    private val customizationState: FragmentState by lazy { TODO() }

    var currentFragmentState: FragmentState = defaultState
        set(value) {
            field.onStateExit()
            field = value
            field.onStateEnter()
        }

    init {
        currentFragmentState = defaultState
    }

}