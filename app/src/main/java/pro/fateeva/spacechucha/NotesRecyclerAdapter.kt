package pro.fateeva.spacechucha

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import pro.fateeva.spacechucha.databinding.AstronomyNoteItemBinding
import pro.fateeva.spacechucha.databinding.SpaceNoteItemBinding
import pro.fateeva.spacechucha.repository.Note
import pro.fateeva.spacechucha.view.TYPE_SPACE
import java.text.SimpleDateFormat
import java.util.*

class NotesRecyclerAdapter(var notesList: List<Note>, private val callbackListener: MyCallback) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_SPACE -> {
                val bindingViewHolder = SpaceNoteItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                SpaceViewHolder(bindingViewHolder.root)
            }
            else -> {
                val bindingViewHolder = AstronomyNoteItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                AstronomyViewHolder(bindingViewHolder.root)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return notesList[position].type
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        return when (getItemViewType(position)) {
            TYPE_SPACE -> {
                (holder as SpaceViewHolder).bind(notesList[position])
            }
            else -> {
                (holder as AstronomyViewHolder).bind(notesList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    inner class SpaceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(note: Note) {
            SpaceNoteItemBinding.bind(itemView).apply {
                textTextView.text = note.text
                dateTextView.text = note.date

                cardView.setOnClickListener {
                    callbackListener.onClick(this@SpaceViewHolder.adapterPosition)
                }
            }
        }
    }

    inner class AstronomyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(note: Note) {
            AstronomyNoteItemBinding.bind(itemView).apply {
                textTextView.text = note.text
                dateTextView.text = note.date

                cardView.setOnClickListener {
                    callbackListener.onClick(this@AstronomyViewHolder.adapterPosition)
                }
            }
        }
    }
}

class DiffUtilCallback(oldList: List<Note>, newList: List<Note>) : DiffUtil.Callback() {
    val oldList: List<Note> = oldList
    val newList: List<Note> = newList

    override fun getOldListSize(): Int = oldList?.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldNote: Note = oldList[oldItemPosition]
        val newNote: Note = newList[newItemPosition]
        return oldNote.id === newNote.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldNote: Note = oldList[oldItemPosition]
        val newNote: Note = newList[newItemPosition]
        return oldNote == newNote
    }
}
