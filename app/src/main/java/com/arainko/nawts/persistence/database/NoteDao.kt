package com.arainko.nawts.persistence.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.arainko.nawts.persistence.entities.Note

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes ORDER BY `order` DESC")
    fun getNotes(): LiveData<List<Note>>

    @Insert
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Update
    suspend fun update(notes: List<Note>)

    @Delete
    suspend fun delete(note: Note)
}