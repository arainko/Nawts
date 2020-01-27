package com.arainko.nawts.persistence

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey
    var id: Int,

    @ColumnInfo(name = "header")
    var header: String,

    @ColumnInfo(name = "content")
    var content: String
)