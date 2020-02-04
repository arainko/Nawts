package com.arainko.nawts.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.arainko.nawts.persistence.Note
import com.arainko.nawts.persistence.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class NoteViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {
    val repository: Repository = Repository(application)
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun addNote(header: String, content: String) {
        launch(coroutineContext) {
            repository.insert(Note(header, content))
        }
    }

    fun updateNote(header: String, content: String, id: Int) {
        launch(coroutineContext) {
            repository.update(Note(header, content, id))
        }
    }

    fun deleteNote(header: String, content: String, id: Int) {
        launch(coroutineContext) {
            repository.update(Note(header, content, id))
        }
    }

    inner class Actions {
        val addAction: (String, String) -> Unit = { header, content -> addNote(header, content) }
        val updateAction: (String, String, Int) -> Unit = { header, content, id -> updateNote(header, content, id) }
        val deleteAction: (Int) -> Unit = { deleteNote("", "", it) }
    }
}