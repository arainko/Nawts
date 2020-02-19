package com.arainko.nawts.persistence.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "notes")
data class Note(
    @ColumnInfo(name = "header")
    var header: String,

    @ColumnInfo(name = "content")
    var content: String,

    @Embedded
    var style: NoteStyle = NoteStyle("#ffffff", "#ffffff"),

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
) : Parcelable