package pro.fateeva.spacechucha

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pro.fateeva.spacechucha.view.PictureOfTheDayFragment
import pro.fateeva.spacechucha.view.ThemesDialogFragment
import pro.fateeva.spacechucha.view.ThemesDialogFragment.Companion.MARS
import pro.fateeva.spacechucha.view.ThemesDialogFragment.Companion.MOON
import pro.fateeva.spacechucha.view.ThemesDialogFragment.Companion.THEME
import pro.fateeva.spacechucha.view.ThemesDialogFragment.Companion.THEME_NAME

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val prefs: SharedPreferences = getSharedPreferences(THEME, MODE_PRIVATE)
        val theme: String = prefs.getString(THEME_NAME, null).toString()

        when(theme){
            MARS -> setTheme(R.style.MarsTheme)
            MOON -> setTheme(R.style.MoonTheme)
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, PictureOfTheDayFragment.newInstance()).commit()
    }
}