package pro.fateeva.spacechucha

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import pro.fateeva.spacechucha.databinding.ActivityMainBinding
import pro.fateeva.spacechucha.view.*
import pro.fateeva.spacechucha.view.ThemesDialogFragment.Companion.MARS
import pro.fateeva.spacechucha.view.ThemesDialogFragment.Companion.MOON
import pro.fateeva.spacechucha.view.ThemesDialogFragment.Companion.THEME
import pro.fateeva.spacechucha.view.ThemesDialogFragment.Companion.THEME_NAME

private const val NUM_PAGES = 5

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mPager: ViewPager

    private var fragmentsList = listOf(
        PictureOfTheDayFragment(),
        EarthFragment(),
        MarsFragment(),
        MoonFragment(),
        SettingsFragment()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs: SharedPreferences = getSharedPreferences(THEME, MODE_PRIVATE)
        val theme: String = prefs.getString(THEME_NAME, null).toString()

        when (theme) {
            MARS -> setTheme(R.style.MarsTheme)
            MOON -> setTheme(R.style.MoonTheme)
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        mPager = binding.pager
        val pagerAdapter = PagerAdapter(supportFragmentManager, fragmentsList)
        mPager.adapter = pagerAdapter

        initBottomNavigation()
    }

    override fun onBackPressed() {
        if (mPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            mPager.currentItem = mPager.currentItem - 1
        }
    }

    fun initBottomNavigation() {
        mPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
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
            mPager.currentItem = when (item.itemId) {
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

    private inner class PagerAdapter(fm: FragmentManager, fragmentsList: List<Fragment>) :
        FragmentStatePagerAdapter(fm) {
        override fun getCount(): Int = NUM_PAGES
        override fun getItem(position: Int): Fragment {
            return fragmentsList[position]
        }
    }
}
