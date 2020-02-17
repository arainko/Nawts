package com.arainko.nawts.persistence

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class NoteViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {
    private val repository: Repository = Repository(application)
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    val notes: LiveData<List<Note>> by lazy { repository.getNotes() }

    fun addNote(note: Note) {
        launch(coroutineContext) {
            repository.insert(note)
        }
    }

    fun updateNote(note: Note) {
        launch(coroutineContext) {
            repository.update(note)
        }
    }

    fun deleteNote(note: Note) {
        launch(coroutineContext) {
            repository.delete(note)
        }
    }

}