package pro.fateeva.spacechucha.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class NotesRepositoryImpl : NotesRepository {

    private val mutableLiveData: MutableLiveData<List<Note>> = MutableLiveData()
    override val liveData: LiveData<List<Note>> = mutableLiveData

    private val noteList = mutableListOf<Note>()

    override fun addNote(note: Note) {
        noteList.add(note)
        mutableLiveData.value = noteList
    }

    override fun deleteNote(note: Note) {
        noteList.remove(note)
    }

    override fun getNotesList(): List<Note> = noteList

    override fun getSize(): Int = noteList.size
}