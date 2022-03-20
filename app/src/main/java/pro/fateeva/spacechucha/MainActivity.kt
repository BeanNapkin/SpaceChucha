package pro.fateeva.spacechucha

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pro.fateeva.spacechucha.view.PictureOfTheDayFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, PictureOfTheDayFragment.newInstance()).commit()
    }
}