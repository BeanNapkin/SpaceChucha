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

class EarthViewModel(
    private val retrofitImpl: RetrofitImpl = RetrofitImpl()
) : ViewModel() {
    private val earthEpicLiveData: MutableLiveData<LoadableData<EarthEpicServerResponseData>> = MutableLiveData()
    private val asteroidsLiveData: MutableLiveData<LoadableData<AsteroidsResponseData>> = MutableLiveData()
    private val currentDateRepository: CurrentDateRepository = CurrentDateRepositoryImpl

    fun getEpicData() = earthEpicLiveData
    fun getAsteroidsData() = asteroidsLiveData
    fun getCurrentDateData() = currentDateRepository.currentDate

    fun getEarthEpicAndAsteroidsData(date: String) {
        earthEpicLiveData.value = LoadableData.Loading(0)
        asteroidsLiveData.value = LoadableData.Loading(0)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            earthEpicLiveData.value = LoadableData.Error(Throwable("wrong key"))
        } else {
            retrofitImpl.getRetrofitImpl().getEPICByDate(date, apiKey).enqueue(callbackEarthEpic)
            retrofitImpl.getRetrofitImpl().getDataAboutAsteroidsByDate(date, apiKey).enqueue(callbackAsteroidsData)
        }
    }

    private val callbackEarthEpic = object : Callback<List<EarthEpicServerResponseData>> {
        override fun onResponse(
            call: Call<List<EarthEpicServerResponseData>>,
            response: Response<List<EarthEpicServerResponseData>>
        ) {
            if (response.isSuccessful && response.body() != null) {
                earthEpicLiveData.postValue(
                    response.body()!!.lastOrNull()?.let { Success(it) }
                        ?: LoadableData.Error(RuntimeException("No Image")))
            } else {
                earthEpicLiveData.value =
                    LoadableData.Error(Throwable("epic response is not success or body is null"))
            }
        }

        override fun onFailure(call: Call<List<EarthEpicServerResponseData>>, t: Throwable) {
            earthEpicLiveData.value =
                LoadableData.Error(Throwable("epic response is failure: " + t.message))
        }
    }

    private val callbackAsteroidsData = object : Callback<AsteroidsResponseData> {
        override fun onResponse(
            call: Call<AsteroidsResponseData>,
            response: Response<AsteroidsResponseData>
        ) {
            if (response.isSuccessful && response.body() != null) {
                asteroidsLiveData.postValue(LoadableData.Success(response.body()!!))
            } else {
                asteroidsLiveData.value =
                    LoadableData.Error(Throwable("asteroids data response is not success or body is null"))
            }
        }

        override fun onFailure(call: Call<AsteroidsResponseData>, t: Throwable) {
            asteroidsLiveData.value =
                LoadableData.Error(Throwable("asteroids data response is failure: " + t.message))
        }

    }


}