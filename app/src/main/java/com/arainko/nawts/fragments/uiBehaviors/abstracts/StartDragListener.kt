package com.arainko.nawts.fragments.uiBehaviors.abstracts

import androidx.recyclerview.widget.RecyclerView

interface StartDragListener {
    fun requestDrag(viewHolder: RecyclerView.ViewHolder)
}