package com.arainko.nawts.view

import android.view.View
import com.arainko.nawts.model.DatabaseActions
import com.arainko.nawts.persistence.Note

abstract class NoteHolderBehavior(protected val dbActions: DatabaseActions) {
    abstract val onClick: (Note, View) -> Unit
    abstract val onLongClick: (Note, View) -> Boolean
}