package pro.fateeva.spacechucha.view.notes

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import pro.fateeva.spacechucha.databinding.AstronomyNoteItemBinding
import pro.fateeva.spacechucha.databinding.SpaceNoteItemBinding
import pro.fateeva.spacechucha.repository.Note
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

    inner class SpaceViewHolder(view: View) : RecyclerView.ViewHolder(view),
        ItemTouchHelperViewHolder {
        fun bind(note: Note) {
            SpaceNoteItemBinding.bind(itemView).apply {
                textTextView.text = note.text
                dateTextView.text = note.date

                cardView.setOnClickListener {
                    callbackListener.onClick(this@SpaceViewHolder.adapterPosition)
                }
            }
        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.CYAN)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(0)
        }
    }

    inner class AstronomyViewHolder(view: View) : RecyclerView.ViewHolder(view),
        ItemTouchHelperViewHolder {
        fun bind(note: Note) {
            AstronomyNoteItemBinding.bind(itemView).apply {
                textTextView.text = note.text
                dateTextView.text = note.date

                cardView.setOnClickListener {
                    callbackListener.onClick(this@AstronomyViewHolder.adapterPosition)
                }
            }
        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.CYAN)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(0)
        }
    }
}

class DiffUtilCallback(val oldList: List<Note>, val newList: List<Note>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldNote: Note = oldList[oldItemPosition]
        val newNote: Note = newList[newItemPosition]
        return oldNote.id == newNote.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldNote: Note = oldList[oldItemPosition]
        val newNote: Note = newList[newItemPosition]
        return oldNote == newNote
    }
}
