package pro.fateeva.spacechucha.repository

interface NotesRepository {
    fun addNote(note: Note)
    fun deleteNote(note: Note)
    fun getNotesList() : List<Note>
    fun getSize() : Int
}