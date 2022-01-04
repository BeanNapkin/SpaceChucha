package pro.fateeva.spacechucha.viewmodel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import pro.fateeva.spacechucha.repository.CurrentDateRepository
import pro.fateeva.spacechucha.repository.CurrentDateRepositoryImpl

class MainActivityViewModel(
    private val currentDateRepository: CurrentDateRepository = CurrentDateRepositoryImpl
) : ViewModel() {

    fun setCurrentDate(date: String){
        currentDateRepository.setCurrentDate(date)
    }
}