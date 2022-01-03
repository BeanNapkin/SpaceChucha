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
import pro.fateeva.spacechucha.BuildConfig
import pro.fateeva.spacechucha.R
import pro.fateeva.spacechucha.databinding.EarthFragmentBinding
import pro.fateeva.spacechucha.databinding.MarsFragmentBinding
import pro.fateeva.spacechucha.repository.AsteroidsResponseData
import pro.fateeva.spacechucha.repository.EarthEpicServerResponseData
import pro.fateeva.spacechucha.repository.MarsPhotosServerResponseData
import pro.fateeva.spacechucha.viewmodel.EarthViewModel
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

        showDatePicker()

        binding.changeDateButton.setOnClickListener {
            showDatePicker()
        }
    }

    private fun showDatePicker() {
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Выберите дату")
                .build()

        datePicker.show(requireActivity().supportFragmentManager, "tag")

        datePicker.addOnPositiveButtonClickListener {
            date = convertLongToString(it)
            binding.dateTextView.setText(date)
            refresh(date)
        }
    }

    private fun convertLongToString(dateLong: Long): String {
        val date = Date(dateLong)
        val format = SimpleDateFormat("yyyy-MM-dd")
        return format.format(date)
    }

    private fun renderMarsPhoto(state: LoadableData<MarsPhotosServerResponseData>) {
        when (state) {
            is LoadableData.Error -> {
                binding.progressBar.visibility = View.GONE
                Log.e("ImageLoading", "Ошибка при скачке изображения", state.error)
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
                    Snackbar.make(binding.root, "Нет фото для этой даты", Snackbar.LENGTH_SHORT).show()
                } else {
                    val image = marsPhotosServerResponseData.last().image
                    binding.marsImageView.load(image) {
                        lifecycle(this@MarsFragment)
                        error(R.drawable.ic_baseline_error)
                        placeholder(R.drawable.ic_no_image)
                    }
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun refresh(date: String) {
        viewModel.getMarsPhotos(date)
    }

    companion object {
        @JvmStatic
        fun newInstance() = MarsFragment()
    }
}