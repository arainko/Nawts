package com.arainko.nawts.view.viewmodels

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeViewModel : ViewModel() {
    val isVisible = MutableLiveData(true)
    val previewColor = MutableLiveData("#ffffff")

    fun appBarOnClick() {
        isVisible.value = false
    }

}