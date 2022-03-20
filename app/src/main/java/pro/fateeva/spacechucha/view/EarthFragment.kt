package pro.fateeva.spacechucha.view

import android.os.Bundle
import android.transition.Slide
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.transition.*
import coil.load
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.earth_fragment.*
import kotlinx.android.synthetic.main.earth_fragment.main
import kotlinx.android.synthetic.main.picture_of_the_day_fragment.*
import pro.fateeva.spacechucha.BuildConfig
import pro.fateeva.spacechucha.R
import pro.fateeva.spacechucha.databinding.EarthFragmentBinding
import pro.fateeva.spacechucha.repository.AsteroidsResponseData
import pro.fateeva.spacechucha.repository.EarthEpicServerResponseData
import pro.fateeva.spacechucha.utils.fadeInOnLoad
import pro.fateeva.spacechucha.viewmodel.EarthViewModel
import pro.fateeva.spacechucha.viewmodel.LoadableData
import java.text.SimpleDateFormat
import java.util.*

class EarthFragment : Fragment() {

    lateinit var date: String
    private var isExpanded = false

    private var _binding: EarthFragmentBinding? = null
    val binding: EarthFragmentBinding
        get() {
            return _binding!!
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private val viewModel: EarthViewModel by lazy {
        ViewModelProvider(this).get(EarthViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = EarthFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getEpicData().observe(viewLifecycleOwner, Observer {
            renderEpicData(it)
        })

        viewModel.getAsteroidsData().observe(viewLifecycleOwner, Observer {
            renderAsteroidsData(it)
        })

        viewModel.getCurrentDateData().observe(viewLifecycleOwner, Observer {
            refresh(it)
        })

        initImageViewExpanding()
    }

    private fun initImageViewExpanding() {
        binding.earthImageView.setOnClickListener {
            binding.earthExpandedImageView.setImageDrawable(binding.earthImageView.drawable)

            val transition = AutoTransition()
            transition.duration = 200
            TransitionManager.beginDelayedTransition(main, transition)
            binding.earthImageGroup.visibility = View.GONE
            binding.earthExpandedImageView.visibility = View.VISIBLE

        }

        binding.earthExpandedImageView.setOnClickListener {
            val transition = AutoTransition()
            transition.duration = 200
            TransitionManager.beginDelayedTransition(main, transition)
            binding.earthExpandedImageView.visibility = View.GONE
            binding.earthImageGroup.visibility = View.VISIBLE
        }
    }

    private fun renderEpicData(state: LoadableData<EarthEpicServerResponseData>) {
        when (state) {
            is LoadableData.Error -> {
                binding.progressBar.visibility = View.GONE
                Log.e("ImageLoading", "Ошибка при скачке изображения", state.error)
                binding.earthImageView.setImageDrawable(resources.getDrawable(R.drawable.ic_no_image, requireContext().theme))
                Snackbar.make(binding.root, "Нет фото для этой даты", 10000)
                    .show()
            }
            is LoadableData.Loading -> {
                binding.progressBar.visibility = View.VISIBLE
            }
            is LoadableData.Success -> {
                val earthEpicResponseData = state.data
                val date = earthEpicResponseData.date.split(" ").first()
                val image = earthEpicResponseData.image
                val url = "https://api.nasa.gov/EPIC/archive/natural/" +
                        date.replace("-", "/", true) +
                        "/png/" +
                        "$image" +
                        ".png?api_key=${BuildConfig.NASA_API_KEY}"
                binding.earthImageView.load(url) {
                    lifecycle(this@EarthFragment)
                    fadeInOnLoad(binding.earthImageView)
                    error(R.drawable.ic_baseline_error)
                }
                binding.progressBar.visibility = View.GONE
            }
        }
    }


    private fun renderAsteroidsData(state: LoadableData<AsteroidsResponseData>) {
        when (state) {
            is LoadableData.Error -> {
                binding.progressBar.visibility = View.GONE
                Log.e("AsteroidsDataLoading", "Ошибка при скачке данных об астероидах", state.error)
                Snackbar.make(binding.root, "error ", Snackbar.LENGTH_SHORT)
                    .setAction("Retry") {
                        refresh(date)
                    }
                    .show()
            }
            is LoadableData.Loading -> {
                binding.countAsteroidsTextView.setText("loading...")
            }
            is LoadableData.Success -> {
                binding.countAsteroidsTextView.setText(state.data.asteroidsCount)
            }
        }
    }

    private fun refresh(date: String) {
        viewModel.getEarthEpicAndAsteroidsData(date)
    }

    companion object {
        @JvmStatic
        fun newInstance() = EarthFragment()
    }
}