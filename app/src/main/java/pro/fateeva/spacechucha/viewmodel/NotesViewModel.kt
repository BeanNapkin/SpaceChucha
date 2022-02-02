package pro.fateeva.spacechucha.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pro.fateeva.spacechucha.repository.*

class NotesViewModel(
    private val notesRepository: NotesRepository = NotesRepositoryImpl()
) : ViewModel() {

    private val mutableLiveData: MutableLiveData<List<Note>> = MutableLiveData()
    val liveData: LiveData<List<Note>> = mutableLiveData
    private var filterString = ""

    fun saveNote(note: Note) {
        notesRepository.addNote(note)
        mutableLiveData.value = getNotes()
    }

    fun updateNote(note: Note) {
        notesRepository.updateNote(note)
        mutableLiveData.value = getNotes()
    }

    fun getSize() = notesRepository.getSize()
    fun getNotes() = notesRepository.getNotesList().toList()
    fun getNoteListLiveData() = liveData

    fun moveNote(fromPosition: Int, toPosition: Int) {
        notesRepository.moveNote(fromPosition, toPosition)
        mutableLiveData.value = getNotes()
    }

    fun toggleFavourite(position: Int) {
        notesRepository.toggleFavourite(position)
        mutableLiveData.value = getNotes()
    }

    fun deleteNote(id: Int) {
        notesRepository.deleteNote(id)
        mutableLiveData.value = getNotes()
    }

    fun searchNotesByText(text: String) {
        filterString = text
        filterList()
    }

    private fun filterList() {
        mutableLiveData.value = getNotes().filter { it.text.contains(filterString, ignoreCase = true) }
    }
}