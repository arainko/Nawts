package com.arainko.nawts.view.abstracts

import android.view.View

interface HolderBehavior <T> {
    fun onHolderClick(holderItem: T, view: View, position: Int)
    fun onHolderLongClick(holderItem: T, view: View, position: Int): Boolean
}