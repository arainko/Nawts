package com.arainko.nawts.persistence.viewmodel

import androidx.lifecycle.ViewModel
import com.arainko.nawts.persistence.entities.Note
import com.arainko.nawts.view.NoteAdapter
import java.text.FieldPosition
import kotlin.properties.Delegates

class CustomizerCacheViewModel : ViewModel() {
    lateinit var note: Note
    var position: Int by Delegates.notNull()
    lateinit var adapter: NoteAdapter
}