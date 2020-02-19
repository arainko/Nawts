package com.arainko.nawts.persistence.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class NoteStyle(
    @ColumnInfo(name = "background_color")
    var backgroundColor: String,

    @ColumnInfo(name = "stroke_color")
    var strokeColor: String
    )