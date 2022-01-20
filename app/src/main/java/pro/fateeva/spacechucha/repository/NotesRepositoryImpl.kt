package pro.fateeva.spacechucha.repository

class NotesRepositoryImpl : NotesRepository {

    private val noteList = mutableListOf<Note>()

    override fun addNote(note: Note) {
        noteList.add(note)
    }

    override fun deleteNote(note: Note) {
        noteList.remove(note)
    }

    override fun getNotesList(): List<Note> = noteList

    override fun getSize(): Int = noteList.size
}