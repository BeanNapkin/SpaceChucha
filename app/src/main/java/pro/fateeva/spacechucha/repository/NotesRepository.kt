package pro.fateeva.spacechucha.repository

import androidx.lifecycle.LiveData

interface NotesRepository {

    fun addNote(note: Note)
    fun updateNote(note: Note)
    fun deleteNote(note: Note)
    fun deleteNote(position: Int)
    fun getNotesList() : List<Note>
    fun getSize() : Int
    fun moveNote(fromPosition: Int, toPosition: Int)
    fun toggleFavourite(position: Int)
}