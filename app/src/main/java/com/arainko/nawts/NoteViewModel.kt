package com.arainko.nawts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.arainko.nawts.persistence.Repository

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    val repository: Repository = Repository(application)
    val text = "COSTRAMTCOSTAMCORTAMS\nCOS"
    
}