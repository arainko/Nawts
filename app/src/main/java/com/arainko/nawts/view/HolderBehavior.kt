package com.arainko.nawts.view

import android.view.View

interface HolderBehavior <T> {
    fun onHolderClick(holderItem: T, view: View)
    fun onHolderLongClick(holderItem: T, view: View): Boolean
}