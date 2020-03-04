package com.arainko.nawts.view.abstracts

import android.view.View
import androidx.recyclerview.widget.RecyclerView

interface HolderBehavior <T: RecyclerView.ViewHolder> {
    fun onHolderClick(holder: T)
    fun onHolderLongClick(holder: T): Boolean
}