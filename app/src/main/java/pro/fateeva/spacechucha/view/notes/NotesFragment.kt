package pro.fateeva.spacechucha.view.notes

import android.os.Bundle
import android.view.*
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.notes_fragment.*
import kotlinx.android.synthetic.main.space_note_item.*
import pro.fateeva.spacechucha.databinding.NotesFragmentBinding
import pro.fateeva.spacechucha.repository.Note
import pro.fateeva.spacechucha.viewmodel.NotesViewModel
import java.text.SimpleDateFormat
import java.util.*


const val TYPE_ASTRONOMY = 0
const val TYPE_SPACE = 1

class NotesFragment : Fragment(), ItemTouchCallback {

    private var _binding: NotesFragmentBinding? = null
    val binding: NotesFragmentBinding
        get() {
            return _binding!!
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private val viewModel: NotesViewModel by lazy {
        ViewModelProvider(this).get(NotesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = NotesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fab.setOnClickListener {
            if (binding.fabMenuLayout.visibility == View.VISIBLE) {
                binding.fabMenuLayout.visibility = View.GONE
            } else {
                binding.fabMenuLayout.visibility = View.VISIBLE
            }
        }

        binding.spaceNoteTextView.setOnClickListener {
            binding.fabMenuLayout.visibility = View.GONE
            showNoteDialog(noteType = TYPE_SPACE)
        }

        binding.astronomyNoteTextView.setOnClickListener {
            binding.fabMenuLayout.visibility = View.GONE
            showNoteDialog(noteType = TYPE_ASTRONOMY)
        }

        val adapter = NotesRecyclerAdapter(
            viewModel.getNotes(),
            cellClickListener = object : MyCallback {
                override fun onClick(position: Int) {
                    val note = viewModel.getNotes()[position]
                    showNoteDialog(note, note.type)
                }

            }, favouriteClickListener = object : MyCallback {
                override fun onClick(position: Int) {
                    viewModel.toggleFavourite(position)
                }
            })

        binding.recyclerView.adapter = adapter

        ItemTouchHelper(ItemTouchHelperCallback(this)).attachToRecyclerView(recyclerView)

        viewModel.getNoteListLiveData().observe(viewLifecycleOwner) {
            val diffUtilCallback = DiffUtilCallback(adapter.notesList, it)
            val noteDiffResult = DiffUtil.calculateDiff(diffUtilCallback)

            adapter.notesList = it
            noteDiffResult.dispatchUpdatesTo(adapter)
        }

        binding.searchTextInputLayout.setEndIconOnClickListener {
            val text = inputEditText.text.toString()
            viewModel.searchNotesByText(text)
        }
    }

    fun showNoteDialog(note: Note? = null, noteType: Int) {
        val builder = MaterialAlertDialogBuilder(requireContext())

        if (noteType == TYPE_SPACE) {
            builder.setTitle("Космическая заметка")
        } else {
            builder.setTitle("Звёздная заметка")
        }

        val input = EditText(requireContext())

        if (note == null) {
            input.setHint("Введите текст")
            builder.setView(input)
            builder.setPositiveButton("Сохранить") { dialog, which ->
                val newNote =
                    Note(viewModel.getSize() + 1, noteType, getCurrentDate(), input.text.toString())
                viewModel.saveNote(newNote)
            }
        } else {
            input.setText(note.text)
            builder.setView(input)
            builder.setPositiveButton("Сохранить") { dialog, which ->
                val editNote =
                    Note(note.id, note.type, getCurrentDate(), input.text.toString())
                viewModel.updateNote(editNote)
            }
        }

        builder.setNegativeButton(
            "Отмена"
        ) { dialog, which -> dialog.cancel() }
        builder.show()
    }

    private fun getCurrentDate(): String {
        val currentDate = Calendar.getInstance()
        currentDate.add(Calendar.DAY_OF_MONTH, 0)
        val format1 = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        format1.timeZone = TimeZone.getTimeZone("EST")
        return format1.format(currentDate.time)
    }

    companion object {
        @JvmStatic
        fun newInstance() = NotesFragment()
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        viewModel.moveNote(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        viewModel.deleteNote(position)
    }
}