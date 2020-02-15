package com.arainko.nawts.persistence

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @ColumnInfo(name = "header")
    var header: String,

    @ColumnInfo(name = "content")
    var content: String,

    @ColumnInfo(name = "color")
    var color: String = "#ffffff",

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
)