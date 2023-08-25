package com.movieapp.noteapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.movieapp.noteapp.model.Note

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT*FROM note_table ORDER BY id ASC")
    fun getAllNote() : LiveData<List<Note>>

    @Query("UPDATE note_table SET title = :title, note = :note WHERE id = :id")
    suspend fun updateNote(id: Int, title: String, note: String)

    @Query("SELECT * FROM note_table WHERE title LIKE '%' || :q || '%' OR note LIKE '%' || :q || '%'")
    suspend fun searchNote(q:String?) : List<Note>

}