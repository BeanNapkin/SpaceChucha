package pro.fateeva.spacechucha.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import pro.fateeva.spacechucha.R
import pro.fateeva.spacechucha.databinding.MarsFragmentBinding
import pro.fateeva.spacechucha.databinding.MoonFragmentBinding
import pro.fateeva.spacechucha.repository.MarsPhotosServerResponseData
import pro.fateeva.spacechucha.repository.MarsTempServerResponseData
import pro.fateeva.spacechucha.viewmodel.LoadableData
import pro.fateeva.spacechucha.viewmodel.MarsViewModel
import java.text.SimpleDateFormat
import java.util.*

class MoonFragment : Fragment() {

    lateinit var date: String

    private var _binding: MoonFragmentBinding? = null
    val binding: MoonFragmentBinding
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
        _binding = MoonFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        renderMoonPhoto()
    }

    private fun renderMoonPhoto() {
        binding.progressBar.visibility = View.VISIBLE
        binding.moonImageView.load("https://trek.nasa.gov/tiles/Moon/EQ/LRO_WAC_Mosaic_Global_303ppd_v02/1.0.0/default/default028mm/0/0/0.jpg") {
            lifecycle(this@MoonFragment)
            error(R.drawable.ic_baseline_error)
            placeholder(R.drawable.ic_no_image)
        }
        binding.progressBar.visibility = View.GONE
    }

    companion object {
        @JvmStatic
        fun newInstance() = MoonFragment()
    }
}