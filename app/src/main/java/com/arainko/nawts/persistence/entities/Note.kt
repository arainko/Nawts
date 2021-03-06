package com.arainko.nawts.persistence.entities

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "notes")
data class Note(
    var header: String,

    var content: String,

    @Embedded
    var style: NoteStyle = NoteStyle("#ffffff", "#00000000"),

    @ColumnInfo(name = "list_order")
    var listOrder: Int = 0,

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
) : Parcelable {
    val transitionName: String
        get() = "note$id"

}