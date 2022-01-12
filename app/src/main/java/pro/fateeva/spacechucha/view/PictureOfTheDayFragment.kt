package pro.fateeva.spacechucha.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.transition.Slide
import android.transition.TransitionManager
import android.util.Log
import android.view.*
import android.widget.FrameLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.description_bottomsheet_fragment.view.*
import kotlinx.android.synthetic.main.picture_of_the_day_fragment.*
import pro.fateeva.spacechucha.R
import pro.fateeva.spacechucha.databinding.PictureOfTheDayFragmentBinding
import pro.fateeva.spacechucha.repository.PictureOfTheDayResponseData
import pro.fateeva.spacechucha.viewmodel.LoadableData
import pro.fateeva.spacechucha.viewmodel.PictureOfTheDayViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates


private const val IMAGE = "image"

class PictureOfTheDayFragment : Fragment() {

    private var _binding: PictureOfTheDayFragmentBinding? = null
    val binding: PictureOfTheDayFragmentBinding
        get() {
            return _binding!!
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
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

        viewModel.getCurrentDateData().observe(viewLifecycleOwner, Observer {
            refresh(it)
        })

        setBottomSheetBehavior()

        addWikiSearch()

//        setBottomAppBar(view)
//        initChips()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.favourite -> Toast.makeText(context, "Favourite", Toast.LENGTH_SHORT).show()
//            R.id.settings -> requireActivity().supportFragmentManager.beginTransaction()
//                .replace(R.id.container, SettingsFragment.newInstance())
//                .addToBackStack(null)
//                .commit()
//            android.R.id.home -> {
//                activity?.let {
//                    BottomDrawerFragment().show(it.supportFragmentManager, "tag")
//                }
//            }
//        }
//
//        return super.onOptionsItemSelected(item)
//    }

//    private fun initChips(){
//        binding.dayChipGroup.setOnCheckedChangeListener { group, checkedId ->
//            when(checkedId){
//                R.id.today -> refresh(takeDate(0))
//                R.id.yesterday -> refresh(takeDate(-1))
//                R.id.dayBeforeYesterday -> refresh(takeDate(-2))
//            }
//        }
//        binding.dayChipGroup.check(R.id.today)
//    }

//    private fun setBottomAppBar(view: View) {
//        val context = activity as MainActivity
//        context.setSupportActionBar(binding.bottomAppBar)
//        setHasOptionsMenu(true)
//
//        fab.setOnClickListener {
//            if (isMain) {
//                isMain = false
//                binding.bottomAppBar.navigationIcon = null
//                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
//                binding.fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_arrow_back))
//                binding.bottomAppBar.replaceMenu(R.menu.other_menu)
//            } else {
//                isMain = true
//                binding.bottomAppBar.navigationIcon =
//                    ContextCompat.getDrawable(context, R.drawable.ic_menu_hamburger)
//                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
//                fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_fab_add))
//                binding.bottomAppBar.replaceMenu(R.menu.main_menu)
//            }
//        }
//    }

    private fun renderData(state: LoadableData<PictureOfTheDayResponseData>) {
        when (state) {
            is LoadableData.Error -> {
                binding.progressBar.visibility = View.GONE
                Log.e(null, "Ошибка при скачке изображения", state.error)
                Snackbar.make(binding.root, "error ", Snackbar.LENGTH_SHORT)
                    .setAction("Retry") {
                        refresh(takeDate(0))
                    }
                    .show()
            }
            is LoadableData.Loading -> {
                binding.progressBar.visibility = View.VISIBLE
            }
            is LoadableData.Success -> {
                binding.progressBar.visibility = View.GONE
                val pictureOfTheDayResponseData = state.data
                val url = pictureOfTheDayResponseData.url

                if (pictureOfTheDayResponseData.mediaType.equals(IMAGE)) {
                    showImage(url!!)
                } else {
                    showAVideoUrl(url!!)
                }

                binding.bottomSheetDescription.header.text = state.data.title
                binding.bottomSheetDescription.description.text =
                    state.data.explanation
            }
        }
    }

    private fun showImage(url: String) = with(binding) {
        binding.imageView.visibility = View.VISIBLE
        binding.videoOfTheDay.visibility = View.GONE
        binding.imageView.load(url) {
            lifecycle(this@PictureOfTheDayFragment)
            error(R.drawable.ic_baseline_error)
            placeholder(R.drawable.ic_no_image)
        }
    }

    private fun showAVideoUrl(videoUrl: String) = with(binding) {
        binding.imageView.visibility = View.GONE
        videoOfTheDay.visibility = View.VISIBLE
        videoOfTheDay.text = "Сегодня есть видео. Вот ссылка, тапай! \n" + "${videoUrl}"
        videoOfTheDay.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(videoUrl)
            }
            startActivity(i)
        }
    }

    private fun refresh(date: String) {
        viewModel.getImageOfTheDay(date)
    }

    private fun takeDate(count: Int): String {
        val currentDate = Calendar.getInstance()
        currentDate.add(Calendar.DAY_OF_MONTH, count)
        val format1 = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        format1.timeZone = TimeZone.getTimeZone("EST")
        return format1.format(currentDate.time)
    }

    private fun setBottomSheetBehavior() {
        val behavior = BottomSheetBehavior.from(binding.bottomSheetDescription.bottomSheetContainer)
        behavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
    }

    private fun addWikiSearch() {
        binding.wikiButton.setOnClickListener {
            binding.wikiButton.visibility = View.GONE
            val transition = Slide(Gravity.END)
            transition.duration = 1000
            TransitionManager.beginDelayedTransition(main, transition)
            binding.inputLayout.visibility = View.VISIBLE
        }

        inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://en.wikipedia.org/wiki/${inputEditText.text.toString()}")
            })
        }
    }

    companion object {
        @JvmStatic
//        var isMain = true
        fun newInstance() = PictureOfTheDayFragment()
    }
}

