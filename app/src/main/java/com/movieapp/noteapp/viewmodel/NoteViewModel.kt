package com.movieapp.noteapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movieapp.noteapp.model.Note
import com.movieapp.noteapp.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private var repository: NoteRepository) : ViewModel(){

    private var _allNote: MutableLiveData<List<Note>> = MutableLiveData()
    private val allNote: LiveData<List<Note>> get() = _allNote
    var errorNote: MutableLiveData<Boolean> = MutableLiveData()
    var emptyNote: MutableLiveData<Boolean> = MutableLiveData()


    private val _searchNote = MutableLiveData<List<Note>>()


    init {
        getNoteDB()
    }

    private fun getNoteDB() {
        val currentNoteList = allNote.value
        if (currentNoteList.isNullOrEmpty()) {
            errorNote.value = true
        } else {
            _allNote.value = repository.getData().value
            errorNote.value = false
        }
    }

    fun getNote() : LiveData<List<Note>>{
        return repository.getData()
    }

    fun addNote(note: Note) = CoroutineScope(Dispatchers.IO).launch {
        repository.insertNote(note)
    }

    fun deleteNote(note: Note) = CoroutineScope(Dispatchers.IO).launch {
        repository.deleteNote(note)
    }
    fun updateNote(note: Note) = CoroutineScope(Dispatchers.IO).launch {
        repository.updateNote(note)
    }

    fun searchNote(q: String?): LiveData<List<Note>> {
        viewModelScope.launch {
            val result = repository.searchNote(q)
            //scope.launch içine alınca burda hata veriyordu düzeldi.Fonksiyonlaru sspend yaptım.
            //Diyorduki sen List istiyon ama Livedata? li değer dönüyor!!Nasıl düzeldi bilmiyom
            //Ve emptyNote de çalıştı.!!
            _searchNote.value=result
            if (result.isEmpty()) {
                emptyNote.value = true
                errorNote.value = false
            } else {
                emptyNote.value = false
                errorNote.value = false
            }
        }

        return _searchNote
    }

}