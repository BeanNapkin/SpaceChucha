package pro.fateeva.spacechucha.repository

import androidx.lifecycle.LiveData

interface NotesRepository {
    val liveData: LiveData<List<Note>>

    fun addNote(note: Note)
    fun deleteNote(note: Note)
    fun getNotesList() : List<Note>
    fun getSize() : Int
}