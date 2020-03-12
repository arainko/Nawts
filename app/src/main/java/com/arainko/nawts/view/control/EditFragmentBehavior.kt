package com.arainko.nawts.view.control

import android.content.Intent
import android.provider.CalendarContract
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.arainko.nawts.R
import com.arainko.nawts.hideKeyboard
import com.arainko.nawts.makeToast
import com.arainko.nawts.view.abstracts.FragmentUIBehavior
import com.arainko.nawts.view.containters.EditFragment
import com.arainko.nawts.view.containters.EditFragmentArgs
import com.joestelmach.natty.Parser
import kotlinx.android.synthetic.main.fragment_edit.*
import org.threeten.bp.Instant


class EditFragmentBehavior(
    fragment: EditFragment,
    private val modelActions: NoteViewModel,
    private val args: EditFragmentArgs
) : FragmentUIBehavior<EditFragment>(fragment),
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
        fragment.fabSave.hide()
        findNavController(fragment).navigateUp()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean = when (item!!.itemId) {
        R.id.bottomBarCustomization -> {
            "Whatevs".makeToast(fragment.requireContext())
            true
        }

        R.id.bottomBarReminder -> {
            val currentTime = Instant.now().toEpochMilli()
            val inferredDates = Parser()
                .parse(fragment.headerField.text.toString() + "\n" + fragment.contentField.text.toString())
            val epochTime = if (inferredDates != null && inferredDates.isNotEmpty()) inferredDates[0].dates[0].time else currentTime
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