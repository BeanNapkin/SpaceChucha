package pro.fateeva.spacechucha.viewmodel

import androidx.lifecycle.ViewModel
import pro.fateeva.spacechucha.repository.*

class NotesViewModel (

    private val notesRepository: NotesRepository = NotesRepositoryImpl()
) : ViewModel() {

    fun saveNote(note: Note) = notesRepository.addNote(note)
    fun updateNote(note: Note) = notesRepository.updateNote(note)
    fun getSize() = notesRepository.getSize()
    fun getNotes() = notesRepository.getNotesList().toList()
    fun getNoteListLiveData() = notesRepository.liveData
    fun moveNote(fromPosition: Int, toPosition: Int) = notesRepository.moveNote(fromPosition, toPosition)
    fun deleteNote(id: Int) = notesRepository.deleteNote(id)


}