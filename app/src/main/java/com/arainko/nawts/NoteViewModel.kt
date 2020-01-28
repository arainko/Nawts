package com.arainko.nawts

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

    fun testAdd() {
        launch(Dispatchers.IO) {
            repository.insert(Note("COS", "TAM"))
        }
    }

}