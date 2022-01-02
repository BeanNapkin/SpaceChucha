package pro.fateeva.spacechucha.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import pro.fateeva.spacechucha.databinding.EarthFragmentBinding
import pro.fateeva.spacechucha.databinding.PictureOfTheDayFragmentBinding

class EarthFragment : Fragment() {

    private var _binding: EarthFragmentBinding? = null
    val binding: EarthFragmentBinding
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
        _binding = EarthFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        var isMain = true
        fun newInstance() = EarthFragment()
    }
}