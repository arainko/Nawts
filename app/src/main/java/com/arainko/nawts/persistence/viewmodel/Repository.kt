package com.arainko.nawts.persistence.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import com.arainko.nawts.persistence.database.NoteDao
import com.arainko.nawts.persistence.database.NoteDatabase
import com.arainko.nawts.persistence.entities.Note

class Repository(application: Application) {
    private val noteDao: NoteDao

    init {
        val db = NoteDatabase.getInstance(application)
        noteDao = db.noteDao()
    }

    fun getNotes(): LiveData<List<Note>> = noteDao.getNotes()

    suspend fun insert(note: Note) = noteDao.insert(note)

    suspend fun delete(note: Note) = noteDao.delete(note)

    suspend fun update(note: Note) = noteDao.update(note)

}