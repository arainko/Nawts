package com.arainko.nawts

import com.arainko.nawts.fragments.MainFragment
import com.arainko.nawts.persistence.NoteViewModel
import com.arainko.nawts.view.CustomizationFragmentState
import com.arainko.nawts.view.DefaultFragmentState

class StateManager(private val fragment: MainFragment, private val model: NoteViewModel) {
    private val defaultState: FragmentState by lazy { DefaultFragmentState(fragment, model) }
    val customizationState: FragmentState by lazy { CustomizationFragmentState(fragment, model) }

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