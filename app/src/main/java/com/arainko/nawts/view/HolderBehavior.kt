package com.arainko.nawts.view

import android.view.View
import com.arainko.nawts.model.DatabaseActions
import com.arainko.nawts.persistence.Note

interface HolderBehavior <T> {
    fun onHolderClick(holderItem: T, view: View)
    fun onHolderLongClick(holderItem: T, view: View): Boolean
}