package com.arainko.nawts.view.viewmodels

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    val isVisible = MutableLiveData(true)
    val previewColor = MutableLiveData(0)

    fun appBarOnClick() {
        isVisible.value = false
    }
}