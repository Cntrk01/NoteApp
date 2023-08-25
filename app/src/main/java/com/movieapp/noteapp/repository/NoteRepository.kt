package com.movieapp.noteapp.repository

import com.movieapp.noteapp.database.NoteDao
import com.movieapp.noteapp.model.Note
import javax.inject.Inject

class NoteRepository @Inject constructor(private val dao: NoteDao) {
    suspend fun insertNote(note: Note) = dao.insertNote(note)
    suspend fun deleteNote(note: Note) = dao.deleteNote(note)
    suspend fun updateNote(note: Note) =dao.updateNote(note.id!!, note.title!!,note.note!!)
    suspend fun searchNote(q:String?) = dao.searchNote(q)
    fun getData() = dao.getAllNote()

}