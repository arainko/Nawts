package com.arainko.nawts.view.control

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.arainko.nawts.persistence.database.Repository
import com.arainko.nawts.persistence.entities.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class NoteViewModel(application: Application) : AndroidViewModel(application),
    CoroutineScope {
    private val repository: Repository =
        Repository(application)

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    val notes: LiveData<List<Note>> = repository.getNotes()

//    fun updateOrder(from: Int, to: Int) {
//        if (from < to) {
//            for (i in from..to)
//        }
//    }

    fun getMaxOrder(): Int {
        val maxOrder = notes.value?.maxBy { it.order }?.order
        return if (maxOrder == null) 0 else maxOrder+1
    }

    fun addNote(note: Note) {
        launch(coroutineContext) {
            repository.insert(note)
        }
    }

    fun updateNote(vararg notes: Note) {
        launch(coroutineContext) {
            repository.update(*notes)
        }
    }

    fun deleteNote(note: Note) {
        launch(coroutineContext) {
            repository.delete(note)
        }
    }

}