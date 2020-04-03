package com.arainko.nawts.view.fragments.editFragment


import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.arainko.nawts.R
import com.arainko.nawts.databinding.FragmentEditBinding
import com.arainko.nawts.extensions.asIntColor
import com.arainko.nawts.extensions.blendARGB
import com.arainko.nawts.extensions.delegates.dataBinding
import com.arainko.nawts.extensions.hideKeyboard
import com.arainko.nawts.extensions.makeToast
import com.arainko.nawts.view.DateInferer
import com.arainko.nawts.view.viewmodels.NoteViewModel
import com.arainko.nawts.view.fragments.MainActivity
import com.arainko.nawts.view.viewmodels.EditViewModel
import com.google.android.material.transition.MaterialContainerTransform
import kotlinx.android.synthetic.main.fragment_edit.*


/**
 * A simple [Fragment] subclass.
 */
class EditFragment : Fragment(),
    View.OnClickListener,
    Toolbar.OnMenuItemClickListener {

    private val args: EditFragmentArgs by navArgs()
    private val noteViewModel: NoteViewModel by activityViewModels()
    private val editViewModel: EditViewModel by viewModels()
    private val binding: FragmentEditBinding by dataBinding(R.layout.fragment_edit)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform(requireContext()).apply {
            duration = resources.getInteger(R.integer.animationSpeed).toLong()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = binding.run {
        viewmodel = editViewModel.apply { note = args.note }
        lifecycleOwner = this@EditFragment
        root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.editFragmentRootLayout.transitionName = args.transitionName
        binding.fabSave.setOnClickListener(this)
        binding.bottomAppBar.setOnMenuItemClickListener(this)
        (requireActivity() as MainActivity).animateSystemBarColors(editViewModel.systemBarColor)
    }

    override fun onResume() {
        super.onResume()
        activity!!.window.run {
            statusBarColor = editViewModel.systemBarColor
            navigationBarColor = editViewModel.systemBarColor
        }
    }

    override fun onPause() {
        super.onPause()
        val defaultColor = resources.getColor(R.color.colorAccent, null)
        (requireActivity() as MainActivity).animateSystemBarColors(defaultColor)
    }

    override fun onClick(v: View?) {
        val newPos = args.itemCount
        val note = editViewModel.note.apply {
            header = binding.headerField.text.toString()
            content = binding.contentField.text.toString()
        }

        if (binding.headerField.text.isNotEmpty() || binding.contentField.text.isNotEmpty()) {
            if (note.id != 0) noteViewModel.updateNotes(note)
            else noteViewModel.addNote(note.apply { listOrder = newPos })
        } else {
            noteViewModel.deleteNote(note)
            "Discarded empty note".makeToast(context, false)
        }

        binding.headerField.hideKeyboard()
        binding.contentField.hideKeyboard()
        findNavController().navigateUp()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean = when (item!!.itemId) {
        R.id.bottomBarCustomization -> {
            "Whatevs".makeToast(requireContext())
            true
        }

        R.id.bottomBarReminder -> {
            val epochTime = DateInferer.inferEpochDate("${binding.headerField.text}\n${binding.contentField.text}")
            val calendarIntent = Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, epochTime)
                .putExtra(CalendarContract.Events.TITLE, binding.headerField.text.toString())
            ContextCompat.startActivity(requireContext(), calendarIntent, null)
            true
        }

        else -> false
    }
}
