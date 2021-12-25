package pro.fateeva.spacechucha

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pro.fateeva.spacechucha.view.PictureOfTheDayFragment
import pro.fateeva.spacechucha.view.ThemesDialogFragment
import pro.fateeva.spacechucha.view.ThemesDialogFragment.Companion.THEME
import pro.fateeva.spacechucha.view.ThemesDialogFragment.Companion.THEME_NAME

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val prefs: SharedPreferences = getSharedPreferences(THEME, MODE_PRIVATE)
        val theme: String = prefs.getString(THEME_NAME, null).toString()

        when(theme){
            "mars" -> setTheme(R.style.MarsTheme)
            "moon" -> setTheme(R.style.MoonTheme)
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, PictureOfTheDayFragment.newInstance()).commit()
    }
}