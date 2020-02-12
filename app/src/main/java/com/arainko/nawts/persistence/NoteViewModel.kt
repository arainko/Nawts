package com.arainko.nawts.persistence

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class NoteViewModel(application: Application) : AndroidViewModel(application), CoroutineScope,
    DatabaseActions {
    private val repository: Repository = Repository(application)
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO
    val notes: LiveData<List<Note>> by lazy { repository.getNotes() }

    override val addAction =
        DatabaseAction { header, content, _ ->
            addNote(
                header,
                content
            )
        }
    override val updateAction =
        DatabaseAction { header, content, id ->
            updateNote(
                header,
                content,
                id
            )
        }
    override val deleteAction =
        DatabaseAction { _, _, id ->
            deleteNote(
                "",
                "",
                id
            )
        }

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
            repository.delete(Note(header, content, id))
        }
    }

}