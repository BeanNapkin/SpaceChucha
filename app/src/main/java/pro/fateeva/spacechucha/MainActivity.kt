package pro.fateeva.spacechucha

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import pro.fateeva.spacechucha.databinding.ActivityMainBinding
import pro.fateeva.spacechucha.databinding.PictureOfTheDayFragmentBinding
import pro.fateeva.spacechucha.view.*
import pro.fateeva.spacechucha.view.ThemesDialogFragment.Companion.MARS
import pro.fateeva.spacechucha.view.ThemesDialogFragment.Companion.MOON
import pro.fateeva.spacechucha.view.ThemesDialogFragment.Companion.THEME
import pro.fateeva.spacechucha.view.ThemesDialogFragment.Companion.THEME_NAME

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, PictureOfTheDayFragment.newInstance()).commit()

        initBottomNavigation()

    }

    fun initBottomNavigation() {
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.pictureOfTheDay -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                        .commit()
                    true
                }
                R.id.earth -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, EarthFragment.newInstance())
                        .commit()
                    true
                }
                R.id.mars -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, MarsFragment.newInstance())
                        .commit()
                    true
                }
                R.id.moon -> {
                    //Item tapped
                    true
                }
                R.id.settings -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, SettingsFragment.newInstance())
                        .commit()
                    true
                }
                else -> false
            }
        }
    }
}