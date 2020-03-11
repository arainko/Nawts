package com.arainko.nawts.view.containters


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.arainko.nawts.view.control.NoteViewModel
import com.arainko.nawts.R
import com.arainko.nawts.asIntColor
import com.arainko.nawts.makeToast
import com.arainko.nawts.view.control.EditFragmentBehavior
import kotlinx.android.synthetic.main.fragment_edit.*
import kotlinx.android.synthetic.main.fragment_edit.view.*

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

        bottomAppBar.setOnMenuItemClickListener(fragmentBehavior)

        headerField.addTextChangedListener( onTextChanged = { header, _, _, _ ->
            contentField.setText(header)
        })
    }
}
