package com.arainko.nawts.view.containters


import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.ColorUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.arainko.nawts.R
import com.arainko.nawts.asIntColor
import com.arainko.nawts.blendARGB
import com.arainko.nawts.view.control.EditFragmentBehavior
import com.arainko.nawts.view.control.NoteViewModel
import com.google.android.material.transition.MaterialContainerTransform
import kotlinx.android.synthetic.main.fragment_edit.*


/**
 * A simple [Fragment] subclass.
 */
class EditFragment : Fragment() {

    private val args: EditFragmentArgs by navArgs()
    private val model: NoteViewModel by viewModels()
    private val fragmentBehavior by lazy { EditFragmentBehavior(this, model, args) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_edit, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editFragmentRootLayout.setBackgroundColor(args.note.style.backgroundColor.asIntColor())
        headerField.setText(args.note.header)
        contentField.setText(args.note.content)
        fabSave.setOnClickListener(fragmentBehavior)
        fabSave.backgroundTintList = ColorStateList.valueOf(
            args.note.style.backgroundColor.asIntColor()
                .blendARGB(Color.BLACK, 0.2f)
                .blendARGB(Color.WHITE, 0.4f)
        )

        val fabColor = args.note.style.backgroundColor.asIntColor()
            .blendARGB(Color.WHITE, 0.4f)


//        val colorAnimator = ValueAnimator.ofObject(ArgbEvaluator(),
//            activity!!.window.statusBarColor,
//            args.note.style.backgroundColor
//                .asIntColor()
//                .blendARGB(Color.BLACK, 0.4f)).apply {
//            duration = resources.getInteger(R.integer.animationSpeed).toLong()
//            addUpdateListener {
//                activity!!.window.statusBarColor = it.animatedValue as Int
//            }
//        }
//        colorAnimator.start()

//        activity!!.window.statusBarColor = args.note.style.backgroundColor
//            .asIntColor()
//            .blendARGB(Color.BLACK, 0.4f)

        bottomAppBar.setOnMenuItemClickListener(fragmentBehavior)
        bottomAppBar.backgroundTint = ColorStateList.valueOf(args.note.style.backgroundColor
            .asIntColor()
            .blendARGB(Color.BLACK, 0.4f))
    }

    override fun onResume() {
        super.onResume()
        activity!!.window.statusBarColor = args.note.style.backgroundColor
            .asIntColor()
            .blendARGB(Color.BLACK, 0.4f)
    }

    override fun onPause() {
        super.onPause()

//        val defaultColor = resources.getColor(R.color.colorAccent, null)
//        // TODO: Make it not so ugly
//        val colorAnimator = ValueAnimator.ofObject(ArgbEvaluator(),
//            activity!!.window.statusBarColor,
//            defaultColor
//            ).apply {
//            duration = resources.getInteger(R.integer.animationSpeed).toLong()
//            addUpdateListener {
//                activity!!.window.statusBarColor = it.animatedValue as Int
//            }
//        }
//        colorAnimator.start()

    }

    fun getComplimentColor(color: Int): Int {
        // get existing colors
        val alpha = Color.alpha(color)
        var red = Color.red(color)
        var blue = Color.blue(color)
        var green = Color.green(color)

        // find compliments
        red = red.inv() and 0xff
        blue = blue.inv() and 0xff
        green = green.inv() and 0xff
        return Color.argb(alpha, red, green, blue)
    }


}
