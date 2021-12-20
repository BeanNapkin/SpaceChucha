package pro.fateeva.spacechucha.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.description_bottomsheet_fragment.view.*
import pro.fateeva.spacechucha.R
import pro.fateeva.spacechucha.databinding.PictureOfTheDayFragmentBinding
import pro.fateeva.spacechucha.viewmodel.PictureOfTheDayState
import pro.fateeva.spacechucha.viewmodel.PictureOfTheDayViewModel

class PictureOfTheDayFragment : Fragment() {

    private var _binding: PictureOfTheDayFragmentBinding? = null
    val binding: PictureOfTheDayFragmentBinding
        get() {
            return _binding!!
        }

    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PictureOfTheDayFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getData().observe(viewLifecycleOwner, Observer {
            renderData(it)
        })

        val behavior = BottomSheetBehavior.from(binding.bottomSheetDescription.bottomSheetContainer)
        behavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED

        refresh()
    }

    private fun renderData(state: PictureOfTheDayState) {
        when (state) {
            is PictureOfTheDayState.Error -> {
                binding.progressBar.visibility = View.GONE
                Log.e(null, "Ошибка при скачке изображения", state.error)
                Snackbar.make(binding.root, "error ", Snackbar.LENGTH_SHORT)
                    .setAction("Retry") {
                        refresh()
                    }
                    .show()
            }
            is PictureOfTheDayState.Loading -> {
                binding.progressBar.visibility = View.VISIBLE
            }
            is PictureOfTheDayState.Success -> {
                binding.progressBar.visibility = View.GONE
                val pictureOfTheDayResponseData = state.pictureOfTheDayResponseData
                val url = pictureOfTheDayResponseData.url
                binding.imageView.load(url) {
                    lifecycle(this@PictureOfTheDayFragment)
                    error(R.drawable.ic_baseline_error)
                    placeholder(R.drawable.ic_baseline_no_image)
                }
                binding.bottomSheetDescription.header.text = state.pictureOfTheDayResponseData.title
                binding.bottomSheetDescription.description.text = state.pictureOfTheDayResponseData.explanation
            }
        }
    }

    private fun refresh(){
        viewModel.getImageOfTheDay()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = PictureOfTheDayFragment()
    }

}