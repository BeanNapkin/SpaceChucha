package pro.fateeva.spacechucha.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class NotesRepositoryImpl : NotesRepository {

    private val mutableLiveData: MutableLiveData<List<Note>> = MutableLiveData()
    override val liveData: LiveData<List<Note>> = mutableLiveData

    private val noteList = mutableListOf<Note>()

    override fun addNote(note: Note) {
        noteList.add(note)
        mutableLiveData.value = noteList.toList()
    }

    override fun updateNote(note: Note) {
        noteList[note.id-1]= note
        mutableLiveData.value = noteList.toList()
    }

    override fun deleteNote(note: Note) {
        noteList.remove(note)
        mutableLiveData.value = noteList.toList()
    }

    override fun deleteNote(position: Int) {
        noteList.removeAt(position)
        mutableLiveData.value = noteList.toList()
    }

    override fun getNotesList(): List<Note> = noteList.toList()

    override fun getSize(): Int = noteList.size

    override fun moveNote(fromPosition: Int, toPosition: Int) {
        val note = noteList[fromPosition]
        noteList.remove(noteList[fromPosition])
        noteList.add(toPosition, note)
        mutableLiveData.value = noteList.toList()
    }
}