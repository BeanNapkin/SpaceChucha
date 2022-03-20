package pro.fateeva.spacechucha.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.text.SimpleDateFormat
import java.util.*

object CurrentDateRepositoryImpl : CurrentDateRepository {
    private val currentDateMutableLiveData = MutableLiveData(takeTodayDate())
    override var currentDate: LiveData<String> = currentDateMutableLiveData

    override fun setCurrentDate(date: String) {
        currentDateMutableLiveData.value = date
    }

    private fun takeTodayDate(): String {
        val format = SimpleDateFormat("yyyy-MM-dd")
        return format.format(Date())
    }

}