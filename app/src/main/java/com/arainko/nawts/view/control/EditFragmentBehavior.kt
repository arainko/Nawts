package com.arainko.nawts.view.control

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.provider.CalendarContract
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.arainko.nawts.R
import com.arainko.nawts.hideKeyboard
import com.arainko.nawts.makeToast
import com.arainko.nawts.view.abstracts.FragmentUIBehavior
import com.arainko.nawts.view.containters.EditFragment
import com.arainko.nawts.view.containters.EditFragmentArgs
import kotlinx.android.synthetic.main.fragment_edit.*


class EditFragmentBehavior(
    fragment: EditFragment,
    private val modelActions: NoteViewModel,
    private val args: EditFragmentArgs
) : FragmentUIBehavior<EditFragment>(fragment),
    View.OnClickListener {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onClick(view: View?) {
        val newPos = args.itemCount
        val note = args.note.apply {
            header = fragment.headerField.text.toString()
            content = fragment.contentField.text.toString()
        }

        if (fragment.headerField.text.isNotEmpty() || fragment.contentField.text.isNotEmpty()) {
            if (note.id != 0) modelActions.updateNotes(note)
            else modelActions.addNote(note.apply { listOrder = newPos})
        } else {
            modelActions.deleteNote(note)
            "Discarded empty note".makeToast(fragment.context, false)
        }

//        val startMillis: Long = Calendar.getInstance().run {
//            set(2022, 0, 19, 7, 30)
//            timeInMillis
//        }
//        val endMillis: Long = Calendar.getInstance().run {
//            set(2022, 0, 19, 8, 30)
//            timeInMillis
//        }

//        val intent = Intent(Intent.ACTION_INSERT)
//            .setData(CalendarContract.Events.CONTENT_URI)
//            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startMillis)
//            .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endMillis)
//            .putExtra(CalendarContract.Events.TITLE, "Yoga")
//            .putExtra(CalendarContract.)
//            .putExtra(CalendarContract.Events.DESCRIPTION, "Group class")
//            .putExtra(CalendarContract.Events.EVENT_LOCATION, "The gym")
//            .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
//            .putExtra(Intent.EXTRA_EMAIL, "rowan@example.com,trevor@example.com")
//        startActivity(fragment.requireContext(), intent, null)

        fragment.headerField.hideKeyboard()
        fragment.contentField.hideKeyboard()
        fragment.fabSave.hide()
        findNavController(fragment).navigateUp()
    }
}