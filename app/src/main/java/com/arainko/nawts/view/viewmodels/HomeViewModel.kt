package com.arainko.nawts.view.viewmodels

import android.app.Application
import android.view.View
import androidx.fragment.app.findFragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import com.arainko.nawts.persistence.entities.Note
import com.arainko.nawts.view.fragments.HomeFragmentDirections
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_home.*

class HomeViewModel(application: Application) : NoteViewModel(application) {
    val isVisible = MutableLiveData(true)

    fun appBarOnClick() {
        isVisible.value = false
    }

    fun fabOnClick(fab: FloatingActionButton) {
        val emptyNote = Note("", "")
        val extras = FragmentNavigatorExtras(
            fab to fab.transitionName
        )
        val action =
            HomeFragmentDirections.actionToEditingFragment(
                emptyNote,
                maxOrder,
                fab.transitionName
            )
        fab.hide()
        findNavController(fab.findFragment()).navigate(action, extras)
    }


}