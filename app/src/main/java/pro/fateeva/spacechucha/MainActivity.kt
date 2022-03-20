package pro.fateeva.spacechucha

import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.google.android.material.datepicker.MaterialDatePicker
import pro.fateeva.spacechucha.databinding.ActivityMainBinding
import pro.fateeva.spacechucha.view.*
import pro.fateeva.spacechucha.view.ThemesDialogFragment.Companion.MARS
import pro.fateeva.spacechucha.view.ThemesDialogFragment.Companion.MOON
import pro.fateeva.spacechucha.view.ThemesDialogFragment.Companion.THEME
import pro.fateeva.spacechucha.view.ThemesDialogFragment.Companion.THEME_NAME
import pro.fateeva.spacechucha.viewmodel.MainActivityViewModel
import java.text.SimpleDateFormat
import java.util.*

private const val NUM_PAGES = 5

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var pager: ViewPager
    lateinit var date: String

    private var fragmentsList = listOf(
        PictureOfTheDayFragment(),
        EarthFragment(),
        MarsFragment(),
        MoonFragment(),
        SettingsFragment()
    )

    private val viewModel: MainActivityViewModel by lazy {
        ViewModelProvider(this).get(MainActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs: SharedPreferences = getSharedPreferences(THEME, MODE_PRIVATE)
        val theme: String? = prefs.getString(THEME_NAME, null)

        when (theme) {
            MARS, null -> setTheme(R.style.MarsTheme)
            MOON -> setTheme(R.style.MoonTheme)
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        pager = binding.pager
        pager.setPageTransformer(true, ZoomOutPageTransformer())
        val pagerAdapter = PagerAdapter(supportFragmentManager, fragmentsList)
        pager.adapter = pagerAdapter

        initBottomNavigation()

        binding.appBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.chooseDate -> showDatePicker()
                R.id.`fun` -> FactsFragment.newInstance().show(supportFragmentManager, null)
            }
            true
        }
    }

    private fun showDatePicker(){
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Выберите дату")
                .build()

        datePicker.show(supportFragmentManager, "tag")

        datePicker.addOnPositiveButtonClickListener {
            date = convertLongToString(it)
            binding.appBar.setTitle(date)
            viewModel.setCurrentDate(date)
        }
    }

    private fun convertLongToString(dateLong: Long): String {
        val date = Date(dateLong)
        val format = SimpleDateFormat("yyyy-MM-dd")
        return format.format(date)
    }

    private fun initBottomNavigation() {
        pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) = Unit

            override fun onPageSelected(position: Int) {
                binding.bottomNavigation.selectedItemId = when(position) {
                    0 -> R.id.pictureOfTheDay
                    1 -> R.id.earth
                    2 -> R.id.mars
                    3 -> R.id.moon
                    4 -> R.id.settings
                    else -> R.id.pictureOfTheDay
                }
            }

            override fun onPageScrollStateChanged(state: Int)= Unit
        })
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            pager.currentItem = when (item.itemId) {
                R.id.pictureOfTheDay -> 0
                R.id.earth -> 1
                R.id.mars -> 2
                R.id.moon -> 3
                R.id.settings -> 4
                else -> 0
            }
            true
        }
    }

    private inner class PagerAdapter(fragmentManager: FragmentManager, fragmentsList: List<Fragment>) :
        FragmentStatePagerAdapter(fragmentManager) {
        override fun getCount(): Int = NUM_PAGES
        override fun getItem(position: Int): Fragment {
            return fragmentsList[position]
        }
    }
}
