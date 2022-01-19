package pro.fateeva.spacechucha.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import pro.fateeva.spacechucha.R
import pro.fateeva.spacechucha.databinding.NotesFragmentBinding

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = NotesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_appbar, menu);
        super.onCreateOptionsMenu(menu, inflater)

    }

    companion object {
        @JvmStatic
        fun newInstance() = NotesFragment()
    }

}