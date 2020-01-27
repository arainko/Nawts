package com.arainko.nawts.persistence

import android.app.Application
import androidx.lifecycle.LiveData

class Repository(application: Application) {
    private var noteDao: NoteDao

    init {
        val db = NoteDatabase.getInstance(application)
        noteDao = db.noteDao()
    }

    fun getNotes(): LiveData<List<Note>> = noteDao.getNotes()

    suspend fun insert(note: Note) = noteDao.insert(note)

    suspend fun delete(note: Note) = noteDao.delete(note)

    suspend fun update(note: Note) = noteDao.update(note)
}