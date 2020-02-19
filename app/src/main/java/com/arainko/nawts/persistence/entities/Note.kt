package com.arainko.nawts.persistence.entities

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

    @Embedded
    var style: NoteStyle = NoteStyle(
        "#ffffff",
        "#ffffff"
    ),

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
)