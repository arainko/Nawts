package com.arainko.nawts.persistence.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.arainko.nawts.persistence.entities.Note

@Database(entities = [Note::class], version = 2, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        private var instance: NoteDatabase? = null
        private val lock = Any()

        fun getInstance(context: Context): NoteDatabase = synchronized(lock) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NoteDatabase::class.java, "note_database"
                    ).fallbackToDestructiveMigration()
                        .build()
                }
                return instance!!
            }
    }

}