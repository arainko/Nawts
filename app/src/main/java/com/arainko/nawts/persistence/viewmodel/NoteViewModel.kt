package com.arainko.nawts.persistence.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.arainko.nawts.persistence.entities.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class NoteViewModel(application: Application) : AndroidViewModel(application),
    CoroutineScope, ModelActions {

    private val repository: Repository = Repository(application)
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    val notes: LiveData<List<Note>> by lazy { repository.getNotes() }

    override fun getNoteById(id: Int): Note? = notes.value?.find { it.id == id }

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

    override fun deleteNote(note: Note) {
        launch(coroutineContext) {
            repository.delete(note)
        }
    }

}