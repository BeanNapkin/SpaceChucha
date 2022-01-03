package pro.fateeva.spacechucha.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pro.fateeva.spacechucha.BuildConfig
import pro.fateeva.spacechucha.repository.EarthEpicServerResponseData
import pro.fateeva.spacechucha.repository.RetrofitImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EarthViewModel(

    private val liveDataForViewToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val retrofitImpl: RetrofitImpl = RetrofitImpl()

) : ViewModel() {

    fun getData(): LiveData<AppState> {
        return liveDataForViewToObserve
    }

    fun getEarthEpic(date: String) {
        liveDataForViewToObserve.value = AppState.Loading(0)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            liveDataForViewToObserve.value = AppState.Error(Throwable("wrong key"))
        } else {
            retrofitImpl.getRetrofitImpl().getEPICByDate(date, apiKey).enqueue(callback)
        }
    }

    private val callback = object : Callback<List<EarthEpicServerResponseData>> {
        override fun onResponse(
            call: Call<List<EarthEpicServerResponseData>>,
            response: Response<List<EarthEpicServerResponseData>>
        ) {
            if (response.isSuccessful && response.body() != null) {
                liveDataForViewToObserve.postValue(AppState.SuccessEarthEpic(response.body()!!))
            } else {
                liveDataForViewToObserve.value =
                    AppState.Error(Throwable("response is not success or body is null"))
            }
        }

        override fun onFailure(call: Call<List<EarthEpicServerResponseData>>, t: Throwable) {
            liveDataForViewToObserve.value =
                AppState.Error(Throwable("response is failure: " + t.message))
        }
    }
}