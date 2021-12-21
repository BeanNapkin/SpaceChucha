package pro.fateeva.spacechucha.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.material.bottomappbar.BottomAppBar
import kotlinx.android.synthetic.main.picture_of_the_day_fragment.*
import pro.fateeva.spacechucha.MainActivity
import pro.fateeva.spacechucha.R
import pro.fateeva.spacechucha.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    val binding: FragmentSettingsBinding
        get() {
            return _binding!!
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


    companion object {
        @JvmStatic
        fun newInstance() = SettingsFragment()
    }
}