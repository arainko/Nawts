package com.arainko.nawts.view.fragments.editFragment


import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.arainko.nawts.R
import com.arainko.nawts.asIntColor
import com.arainko.nawts.blendARGB
import com.arainko.nawts.view.viewmodels.NoteViewModel
import com.arainko.nawts.view.fragments.MainActivity
import com.google.android.material.transition.MaterialContainerTransform
import kotlinx.android.synthetic.main.fragment_edit.*


/**
 * A simple [Fragment] subclass.
 */
class EditFragment : Fragment() {

    private val args: EditFragmentArgs by navArgs()
    private val model: NoteViewModel by viewModels()
    private lateinit var fragmentBehavior: EditFragmentBehavior

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
    ): View? = inflater.inflate(R.layout.fragment_edit, container, false)

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentBehavior = EditFragmentBehavior(this, model, args)
        editFragmentRootLayout.setBackgroundColor(args.note.style.backgroundColor.asIntColor())
        editFragmentRootLayout.transitionName = args.transitionName
        headerField.setText(args.note.header)
        contentField.setText(args.note.content)
        fabSave.run {
            setOnClickListener(fragmentBehavior)
            backgroundTintList = ColorStateList.valueOf(
                args.note.style.backgroundColor.asIntColor()
                    .blendARGB(Color.BLACK, 0.2f)
                    .blendARGB(Color.WHITE, 0.4f)
            )
        }
        val barColor = args.note.style.backgroundColor
            .asIntColor()
            .blendARGB(Color.BLACK, 0.4f)
        bottomAppBar.run {
            setOnMenuItemClickListener(fragmentBehavior)
            backgroundTint = ColorStateList.valueOf(barColor)
        }
        (requireActivity() as MainActivity).animateSystemBarColors(barColor)
        requireActivity().window.run {
            navigationBarColor = barColor
        }
    }

    override fun onResume() {
        super.onResume()
        activity!!.window.statusBarColor = args.note.style.backgroundColor
            .asIntColor()
            .blendARGB(Color.BLACK, 0.4f)
    }

    override fun onPause() {
        super.onPause()
        val defaultColor = resources.getColor(R.color.colorAccent, null)
        (requireActivity() as MainActivity).animateSystemBarColors(defaultColor)
    }
}
