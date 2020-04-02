package com.arainko.nawts.view.viewmodels

import android.graphics.drawable.Drawable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arainko.nawts.persistence.entities.Note
import com.google.android.material.button.MaterialButton
import kotlin.properties.Delegates

class CustomizerViewModel : ViewModel() {
    var note: Note by Delegates.notNull()
    var noteAdapterPosition: Int by Delegates.notNull()
    var backgroundColor = MutableLiveData("#ffffff")

    fun onColorButtonClick(
        colorToButtonMap: Map<String, MaterialButton>,
        iconDrawable: Drawable?,
        newColor: String
    ) {
        colorToButtonMap[backgroundColor.value]?.icon = null
        backgroundColor.value = newColor
        colorToButtonMap[newColor]?.icon = iconDrawable
    }
}