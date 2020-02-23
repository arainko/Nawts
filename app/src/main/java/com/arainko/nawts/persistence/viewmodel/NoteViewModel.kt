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
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    val notes: LiveData<List<Note>> = repository.getNotes()

    override fun getMaxOrder(): Int {
        val maxOrder = notes.value?.maxBy { it.order }?.order
        return if (maxOrder == null) 0 else maxOrder+1
    }

    fun updateNoteOrder(adapter: NoteAdapter, fromPos: Int, toPos: Int) {
        val currentNotesAdapter = adapter.currentList.toString()

        val pred: (Int, Note) -> Boolean = if (fromPos < toPos)
            { index, _ -> index in fromPos..toPos }
        else { index, _ -> index in toPos..fromPos }

        val orderMapping: (Note) -> Note = if (fromPos > toPos)
            { note: Note -> note.apply { order -= 1 } }
        else { note: Note -> note.apply { order += 1 } }

        val orderCorrection: Int = if (fromPos > toPos) 1 else -1

        val affectedNotes = adapter.currentList.apply {
            get(fromPos).order = get(toPos).order+orderCorrection
            get(fromPos).toString()
        }
            ?.filterIndexed(pred)
            ?.map(orderMapping)
            .sortedBy { it.order }

        adapter.submitList(affectedNotes)

        updateNotes(affectedNotes!!)
    }

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