package com.arainko.nawts.persistence.viewmodel

import com.arainko.nawts.persistence.entities.Note

interface ModelActions {
    fun getMaxOrder(): Int
    fun addNote(note: Note)
    fun updateNote(note: Note)
    fun updateNotes(notes: List<Note>)
    fun deleteNote(note: Note)
}