package com.movieapp.noteapp.database

import androidx.room.Entity
import androidx.room.RoomDatabase
import com.movieapp.noteapp.model.Note

@androidx.room.Database([Note::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase(){
    abstract fun dao() : NoteDao
}