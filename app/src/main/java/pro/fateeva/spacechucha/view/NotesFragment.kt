package pro.fateeva.spacechucha.view

import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.view.*
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import okhttp3.internal.notify
import pro.fateeva.spacechucha.NotesRecyclerAdapter
import pro.fateeva.spacechucha.R
import pro.fateeva.spacechucha.databinding.NotesFragmentBinding
import pro.fateeva.spacechucha.repository.Note
import pro.fateeva.spacechucha.viewmodel.NotesViewModel
import pro.fateeva.spacechucha.viewmodel.PictureOfTheDayViewModel
import java.text.SimpleDateFormat
import java.util.*

const val TYPE_ASTRONOMY = 0
const val TYPE_SPACE = 1

class NotesFragment : Fragment() {

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
            if(binding.fabMenuLayout.visibility == View.VISIBLE){
                binding.fabMenuLayout.visibility = View.GONE
            } else {
                binding.fabMenuLayout.visibility = View.VISIBLE
            }
        }

        binding.spaceNoteTextView.setOnClickListener {
            binding.fabMenuLayout.visibility = View.GONE
            showNoteDialog(TYPE_SPACE)
        }

        binding.astronomyNoteTextView.setOnClickListener {
            binding.fabMenuLayout.visibility = View.GONE
            showNoteDialog(TYPE_ASTRONOMY)
        }

        val adapter = NotesRecyclerAdapter(viewModel.getNotes())
        binding.recyclerView.adapter = adapter

        viewModel.getNoteListLiveData().observe(viewLifecycleOwner){
            adapter.notesList = it
            adapter.notifyDataSetChanged()
        }
    }

    fun showNoteDialog(noteType : Int) {
        val builder = MaterialAlertDialogBuilder(requireContext())

        if (noteType == TYPE_SPACE){
            builder.setTitle("Космическая заметка")
        } else {
            builder.setTitle("Звёздная заметка")
        }

        val input = EditText(requireContext())
        input.setHint("Введите текст")
        builder.setView(input)

        builder.setPositiveButton("Сохранить", DialogInterface.OnClickListener { dialog, which ->
            val id: Int

            if (viewModel.getSize() == null){
                 id = 0
            } else {
                id = viewModel.getSize() + 1
            }
            val note = Note(id, noteType, getCurrentDate(), input.text.toString())
            viewModel.saveNote(note)
        })

        builder.setNegativeButton(
            "Отмена",
            DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
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
}