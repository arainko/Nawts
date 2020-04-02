package com.arainko.nawts.view.fragments.editFragment

import android.content.Intent
import android.provider.CalendarContract
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.arainko.nawts.R
import com.arainko.nawts.extensions.hideKeyboard
import com.arainko.nawts.extensions.makeToast
import com.arainko.nawts.view.DateInferer
import com.arainko.nawts.view.abstracts.FragmentHandler
import com.arainko.nawts.view.viewmodels.NoteViewModel
import kotlinx.android.synthetic.main.fragment_edit.*


class EditFragmentBehavior(
    fragment: EditFragment,
    private val modelActions: NoteViewModel,
    private val args: EditFragmentArgs
) : FragmentHandler<EditFragment>(fragment),
    View.OnClickListener,
    Toolbar.OnMenuItemClickListener {


    override fun onClick(view: View?) {
        val newPos = args.itemCount
        val note = args.note.apply {
            header = fragment.headerField.text.toString()
            content = fragment.contentField.text.toString()
        }

        if (fragment.headerField.text.isNotEmpty() || fragment.contentField.text.isNotEmpty()) {
            if (note.id != 0) modelActions.updateNotes(note)
            else modelActions.addNote(note.apply { listOrder = newPos })
        } else {
            modelActions.deleteNote(note)
            "Discarded empty note".makeToast(fragment.context, false)
        }

        fragment.headerField.hideKeyboard()
        fragment.contentField.hideKeyboard()
        findNavController(fragment).navigateUp()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean = when (item!!.itemId) {
        R.id.bottomBarCustomization -> {
            "Whatevs".makeToast(fragment.requireContext())
            true
        }

        R.id.bottomBarReminder -> {
            val epochTime = DateInferer.inferEpochDate("${fragment.headerField.text}\n${fragment.contentField.text}")
            val calendarIntent = Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, epochTime)
                .putExtra(CalendarContract.Events.TITLE, fragment.headerField.text.toString())
            startActivity(fragment.requireContext(), calendarIntent, null)
            true
        }

        else -> false
    }
}