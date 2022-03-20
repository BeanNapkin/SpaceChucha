package pro.fateeva.spacechucha.view

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.facts_fragment.*
import pro.fateeva.spacechucha.R
import pro.fateeva.spacechucha.databinding.FactsFragmentBinding
import pro.fateeva.spacechucha.databinding.MarsFragmentBinding
import pro.fateeva.spacechucha.databinding.MoonFragmentBinding
import pro.fateeva.spacechucha.repository.MarsPhotosServerResponseData
import pro.fateeva.spacechucha.repository.MarsTempServerResponseData
import pro.fateeva.spacechucha.viewmodel.EarthViewModel
import pro.fateeva.spacechucha.viewmodel.FactsViewModel
import pro.fateeva.spacechucha.viewmodel.LoadableData
import pro.fateeva.spacechucha.viewmodel.MarsViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.android.synthetic.main.facts_fragment.fabImageView as fabImageView1

class FactsFragment : DialogFragment() {

    lateinit var date: String

    private var _binding: FactsFragmentBinding? = null
    val binding: FactsFragmentBinding
        get() {
            return _binding!!
        }

    private val viewModel: FactsViewModel by lazy {
        ViewModelProvider(this).get(FactsViewModel::class.java)
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
        _binding = FactsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factsList = viewModel.getFacts()
        var factNumber: Int = 0

        binding.factTextView.setText(factsList[factNumber])

        binding.fab.setOnClickListener {
            ObjectAnimator.ofFloat(binding.fabImageView,"rotation", -315f, 0f).setDuration(500).start()
            ObjectAnimator.ofFloat(binding.fab, "scaleX", 1.2f, 1f).setDuration(500).start()
            ObjectAnimator.ofFloat(binding.fab, "scaleY", 1.2f, 1f).setDuration(500).start()
            
            if (factNumber == factsList.size - 1) {
                factNumber = 0
            } else {
                factNumber++
            }
            binding.factTextView.setText(factsList[factNumber])
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = FactsFragment()
    }
}