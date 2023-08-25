package com.movieapp.noteapp.module

import android.content.Context
import androidx.room.Room
import com.movieapp.noteapp.database.Database
import com.movieapp.noteapp.database.NoteDao
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@dagger.Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context) : Database{
        return Room.databaseBuilder(appContext,Database::class.java,"note_app")
            .fallbackToDestructiveMigration()
            .build()
    }
    @Provides
    @Singleton
    fun provideDao(database: Database) : NoteDao{
        return database.dao()
    }
}