package pro.fateeva.spacechucha.repository

const val TYPE_ASTRONOMY = "astronomy"
const val TYPE_SPACE = "space"

data class Note(
    val id: Int,
    val type: Int,
    var date: String,
    var text: String,
    val isFavourite: Boolean = false
)
