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
import com.google.android.material.snackbar.Snackbar
import pro.fateeva.spacechucha.BuildConfig
import pro.fateeva.spacechucha.R
import pro.fateeva.spacechucha.databinding.EarthFragmentBinding
import pro.fateeva.spacechucha.viewmodel.AppState
import pro.fateeva.spacechucha.viewmodel.EarthViewModel

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
        viewModel.getData().observe(viewLifecycleOwner, Observer {
            renderData(it)
        })

        refresh()
    }

    private fun renderData(state: AppState) {
        when (state) {
            is AppState.Error -> {
                binding.progressBar.visibility = View.GONE
                Log.e(null, "Ошибка при скачке изображения", state.error)
                Snackbar.make(binding.root, "error ", Snackbar.LENGTH_SHORT)
                    .setAction("Retry") {
                        refresh()
                    }
                    .show()
            }
            is AppState.Loading -> {
                binding.progressBar.visibility = View.VISIBLE
            }
            is AppState.SuccessEarthEpic -> {
                val earthEpicResponseData = state.earthEpicServerResponseData.last()
                val date = earthEpicResponseData.date.split(" ").first()
                val image = earthEpicResponseData.image
                val url = "https://api.nasa.gov/EPIC/archive/natural/" +
                        date.replace("-","/",true) +
                        "/png/" +
                        "$image" +
                        ".png?api_key=${BuildConfig.NASA_API_KEY}"
                binding.earthImageView.load(url) {
                    lifecycle(this@EarthFragment)
                    error(R.drawable.ic_baseline_error)
                    placeholder(R.drawable.ic_no_image)
                }
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun refresh() {
        viewModel.getEarthEpic()
    }

    companion object {
        @JvmStatic
        fun newInstance() = EarthFragment()
    }
}