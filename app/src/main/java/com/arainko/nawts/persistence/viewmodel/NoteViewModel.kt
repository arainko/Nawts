package com.arainko.nawts.persistence.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.arainko.nawts.persistence.entities.Note
import com.arainko.nawts.view.NoteAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class NoteViewModel(application: Application) : AndroidViewModel(application),
    CoroutineScope, ModelActions {
    private val repository: Repository = Repository(application)

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    val notes: LiveData<List<Note>> = repository.getNotes()

    override fun getMaxOrder(): Int {
        val maxOrder = notes.value?.maxBy { it.order }?.order
        return if (maxOrder == null) 0 else maxOrder+1
    }

    override fun addNote(note: Note) {
        launch(coroutineContext) {
            repository.insert(note)
        }
    }

    override fun updateNote(note: Note) {
        launch(coroutineContext) {
            repository.update(note)
        }
    }

    override fun updateNotes(notes: List<Note>) {
        launch(coroutineContext) {
            repository.update(notes)
        }
    }

    override fun deleteNote(note: Note) {
        launch(coroutineContext) {
            repository.delete(note)
        }
    }

}