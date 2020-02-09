package com.arainko.nawts.view

import android.view.View
import com.arainko.nawts.model.DatabaseActions
import com.arainko.nawts.persistence.Note

interface HolderBehavior <T> {
    val onClick: (T, View) -> Unit
    val onLongClick: (T, View) -> Boolean
}