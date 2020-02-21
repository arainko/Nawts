package com.arainko.nawts.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.arainko.nawts.persistence.viewmodel.NoteViewModel
import com.arainko.nawts.R
import com.arainko.nawts.extensions.asIntColor
import com.arainko.nawts.fragments.uiBehaviors.EditFragmentBehavior
import kotlinx.android.synthetic.main.fragment_edit.*

/**
 * A simple [Fragment] subclass.
 */
class EditFragment : Fragment() {

    private val args: EditFragmentArgs by navArgs()
    private val model: NoteViewModel by viewModels()
    private val fragmentBehavior by lazy { EditFragmentBehavior(this, model, args) }

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
    }
}
