package pro.fateeva.spacechucha.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import pro.fateeva.spacechucha.R
import pro.fateeva.spacechucha.databinding.MarsFragmentBinding
import pro.fateeva.spacechucha.repository.MarsPhotosServerResponseData
import pro.fateeva.spacechucha.repository.MarsTempServerResponseData
import pro.fateeva.spacechucha.utils.fadeInOnLoad
import pro.fateeva.spacechucha.viewmodel.LoadableData
import pro.fateeva.spacechucha.viewmodel.MarsViewModel
import java.text.SimpleDateFormat
import java.util.*

class MarsFragment : Fragment() {

    lateinit var date: String

    private var _binding: MarsFragmentBinding? = null
    val binding: MarsFragmentBinding
        get() {
            return _binding!!
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private val viewModel: MarsViewModel by lazy {
        ViewModelProvider(this).get(MarsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MarsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getMarsPhotosData().observe(viewLifecycleOwner, Observer {
            renderMarsPhoto(it)
        })

        viewModel.getMarsWeatherData().observe(viewLifecycleOwner, Observer {
            renderMarsWeather(it)
        })

        viewModel.getCurrentDateData().observe(viewLifecycleOwner, Observer {
            refresh(it)
        })

    }

    private fun renderMarsPhoto(state: LoadableData<MarsPhotosServerResponseData>) {
        when (state) {
            is LoadableData.Error -> {
                binding.progressBar.visibility = View.GONE
                Log.e("ImageLoading", "???????????? ?????? ???????????? ??????????????????????", state.error)
                Snackbar.make(binding.root, "error ", Snackbar.LENGTH_SHORT)
                    .setAction("Retry") {
                        refresh(date)
                    }
                    .show()
            }
            is LoadableData.Loading -> {
                binding.progressBar.visibility = View.VISIBLE
            }
            is LoadableData.Success -> {
                val marsPhotosServerResponseData = state.data.photos
                if (marsPhotosServerResponseData.isEmpty()) {
                    binding.marsImageView.setImageDrawable(resources.getDrawable(R.drawable.ic_no_image, requireContext().theme))
                    Snackbar.make(binding.root, "?????? ???????? ?????? ???????? ????????", 10000)
                        .show()
                } else {
                    val image = marsPhotosServerResponseData.last().image
                    binding.marsImageView.load(image) {
                        lifecycle(this@MarsFragment)
                        fadeInOnLoad(binding.marsImageView)
                        error(R.drawable.ic_baseline_error)
                    }
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun renderMarsWeather(state: LoadableData<MarsTempServerResponseData>) {
        when (state) {
            is LoadableData.Error -> {
                binding.progressBar.visibility = View.GONE
                Log.e("WeatherLoading", "???????????? ?????? ???????????? ????????????", state.error)
                binding.minTempTextView.setText("?????? ???????????? ?? ???????????? ???? ??????????????")
                binding.maxTempTextView.setText("")
            }
            is LoadableData.Loading -> {
                binding.minTempTextView.setText("loading...")
                binding.maxTempTextView.setText("loading...")
            }
            is LoadableData.Success -> {
                val weatherServerResponseData = state.data
                binding.minTempTextView.setText("?????????????????????? ?????????????????????? ??F: " + weatherServerResponseData.min)
                binding.maxTempTextView.setText("???????????????????????? ?????????????????????? ??F: " + weatherServerResponseData.max)
            }
        }
    }

    private fun refresh(date: String) {
        viewModel.getMarsPhotosAndWeather(date)
    }

    companion object {
        @JvmStatic
        fun newInstance() = MarsFragment()
    }
}