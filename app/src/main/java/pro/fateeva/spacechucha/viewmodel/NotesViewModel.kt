package pro.fateeva.spacechucha.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pro.fateeva.spacechucha.BuildConfig
import pro.fateeva.spacechucha.repository.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotesViewModel (

    private val notesRepository: NotesRepository = NotesRepositoryImpl()
) : ViewModel() {

    fun saveNote(note: Note) = notesRepository.addNote(note)
    fun getSize() = notesRepository.getSize()
    fun getNotes() = notesRepository.getNotesList()

    fun getNoteListLiveData() = notesRepository.liveData

}