package com.arainko.nawts.persistence.viewmodel

import com.arainko.nawts.persistence.entities.Note

interface ModelActions {
    fun getNoteById(id: Int): Note?
    fun addNote(note: Note)
    fun updateNote(note: Note)
    fun deleteNote(note: Note)
}