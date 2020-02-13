package com.arainko.nawts.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.arainko.nawts.persistence.NoteViewModel
import com.arainko.nawts.R
import com.arainko.nawts.extensions.hideKeyboard
import com.arainko.nawts.fragments.uiBehaviors.EditFragmentUIBehavior
import kotlinx.android.synthetic.main.fragment_edit.*
import kotlinx.android.synthetic.main.fragment_edit.view.*

/**
 * A simple [Fragment] subclass.
 */
class EditFragment : Fragment() {

    private val args: EditFragmentArgs by navArgs()
    private val model: NoteViewModel by viewModels()
    private val fragmentBehavior by lazy { EditFragmentUIBehavior(this, model, args) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_edit, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.apply {
            headerField.setText(args.header)
            contentField.setText(args.content)
            fabSave.setOnClickListener(fragmentBehavior)
        }
    }
}
