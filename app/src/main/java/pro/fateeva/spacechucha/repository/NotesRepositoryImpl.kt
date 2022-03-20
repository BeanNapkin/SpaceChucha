package pro.fateeva.spacechucha.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class NotesRepositoryImpl : NotesRepository {

    private val noteList = mutableListOf<Note>()

    override fun addNote(note: Note) {
        noteList.add(note)
    }

    override fun updateNote(note: Note) {
        noteList[note.id-1]= note
    }

    override fun deleteNote(note: Note) {
        noteList.remove(note)
    }

    override fun deleteNote(position: Int) {
        noteList.removeAt(position)
    }

    override fun getNotesList(): List<Note> = noteList.toList()

    override fun getSize(): Int = noteList.size

    override fun moveNote(fromPosition: Int, toPosition: Int) {
        val note = noteList[fromPosition]
        noteList.remove(noteList[fromPosition])
        noteList.add(toPosition, note)
    }

    override fun toggleFavourite(position: Int) {
        val newNote = noteList[position].copy(isFavourite = !noteList[position].isFavourite)
        noteList[position] = newNote
    }
}