package com.arainko.nawts.view.viewmodels

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.lifecycle.ViewModel
import com.arainko.nawts.extensions.asIntColor
import com.arainko.nawts.extensions.blendARGB
import com.arainko.nawts.persistence.entities.Note
import kotlin.properties.Delegates

class EditViewModel : ViewModel() {
    var note: Note by Delegates.notNull()

    val backgroundColor: Int
        get() = note.style.backgroundColor.asIntColor()

    val systemBarColor: Int
        get() = note.style.backgroundColor.asIntColor()
            .blendARGB(Color.BLACK, 0.4f)

    val appBarColor: ColorStateList
        get() = ColorStateList.valueOf(systemBarColor)

    val fabColor: ColorStateList
        get() = ColorStateList.valueOf(systemBarColor.blendARGB(Color.WHITE, 0.4f))

}