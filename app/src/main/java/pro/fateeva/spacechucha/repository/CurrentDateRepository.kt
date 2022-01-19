package pro.fateeva.spacechucha.repository

import androidx.lifecycle.LiveData
import java.text.SimpleDateFormat
import java.util.*

interface CurrentDateRepository {
    val currentDate: LiveData<String>
    fun setCurrentDate(date: String)
    fun getCurrentDate() : String
}