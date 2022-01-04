package pro.fateeva.spacechucha.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pro.fateeva.spacechucha.BuildConfig
import pro.fateeva.spacechucha.repository.CurrentDateRepository
import pro.fateeva.spacechucha.repository.CurrentDateRepositoryImpl
import pro.fateeva.spacechucha.repository.PictureOfTheDayResponseData
import pro.fateeva.spacechucha.repository.RetrofitImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PictureOfTheDayViewModel (
    private val liveDataForViewToObserve: MutableLiveData<LoadableData<PictureOfTheDayResponseData>> = MutableLiveData(),
    private val retrofitImpl: RetrofitImpl = RetrofitImpl(),
    private val currentDateRepository: CurrentDateRepository = CurrentDateRepositoryImpl
) : ViewModel() {

    fun getData() = liveDataForViewToObserve
    fun getCurrentDateData() = currentDateRepository.currentDate

    fun getImageOfTheDay(date: String) {
        liveDataForViewToObserve.value = LoadableData.Loading(0)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            liveDataForViewToObserve.value = LoadableData.Error(Throwable("wrong key"))
        } else {
            retrofitImpl.getRetrofitImpl().getPictureOfTheDay(apiKey, date).enqueue(callback)
        }
    }

    private val callback = object : Callback<PictureOfTheDayResponseData> {
        override fun onResponse(
            call: Call<PictureOfTheDayResponseData>,
            response: Response<PictureOfTheDayResponseData>
        ) {
            if (response.isSuccessful && response.body() != null) {
                liveDataForViewToObserve.value = LoadableData.Success(response.body()!!)
            } else {
                liveDataForViewToObserve.value = LoadableData.Error(Throwable("response is not success or body is null"))
            }
        }

        override fun onFailure(call: Call<PictureOfTheDayResponseData>, t: Throwable) {
            liveDataForViewToObserve.value = LoadableData.Error(Throwable("response is failure: " + t.message))
        }
    }
}