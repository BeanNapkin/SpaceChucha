package pro.fateeva.spacechucha.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pro.fateeva.spacechucha.BuildConfig
import pro.fateeva.spacechucha.repository.*
import pro.fateeva.spacechucha.viewmodel.LoadableData.Success
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException

class FactsViewModel(
) : ViewModel() {
    val factsRepository: FactsRepository = FactsRepositoryImpl()

    fun getFacts(): List<String> = factsRepository.getFacts()

}