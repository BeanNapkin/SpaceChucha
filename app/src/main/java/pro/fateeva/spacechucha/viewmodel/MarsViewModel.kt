package pro.fateeva.spacechucha.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.JsonElement
import pro.fateeva.spacechucha.BuildConfig
import pro.fateeva.spacechucha.repository.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MarsViewModel(
    private val retrofitImpl: RetrofitImpl = RetrofitImpl()
) : ViewModel() {
    private val marsPhotoLiveData: MutableLiveData<LoadableData<MarsPhotosServerResponseData>> =
        MutableLiveData()
    private val marsWeatherLiveData: MutableLiveData<LoadableData<MarsTempServerResponseData>> =
        MutableLiveData()
    private val currentDateRepository: CurrentDateRepository = CurrentDateRepositoryImpl

    fun getMarsPhotosData() = marsPhotoLiveData
    fun getMarsWeatherData() = marsWeatherLiveData
    fun getCurrentDateData() = currentDateRepository.currentDate

    fun getMarsPhotosAndWeather(date: String) {
        marsPhotoLiveData.value = LoadableData.Loading(0)
        marsWeatherLiveData.value = LoadableData.Loading(0)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            marsPhotoLiveData.value = LoadableData.Error(Throwable("wrong key"))
        } else {
            retrofitImpl.getRetrofitImpl().getMarsImageByDate(date, apiKey).enqueue(callMarsPhoto)
            retrofitImpl.getRetrofitImpl().getMarsWeather(apiKey).enqueue(callMarsWeather)
        }
    }

    private val callMarsPhoto = object : Callback<MarsPhotosServerResponseData> {
        override fun onResponse(
            call: Call<MarsPhotosServerResponseData>,
            response: Response<MarsPhotosServerResponseData>
        ) {
            if (response.isSuccessful && response.body() != null) {
                marsPhotoLiveData.value = LoadableData.Success(response.body()!!)
            } else {
                marsPhotoLiveData.value =
                    LoadableData.Error(Throwable("mars photos is not success or body is null"))
            }
        }

        override fun onFailure(call: Call<MarsPhotosServerResponseData>, t: Throwable) {
            marsPhotoLiveData.value =
                LoadableData.Error(Throwable("mars photos is failure: " + t.message))
        }
    }

    private val callMarsWeather = object : Callback<Map<String, JsonElement>> {
        override fun onResponse(
            call: Call<Map<String, JsonElement>>,
            response: Response<Map<String, JsonElement>>
        ) {
            if (response.isSuccessful && response.body() != null) {
                val map = response.body()!!
                var weatherData: MarsWeatherServerResponseData? = null
                val solKeys = map["sol_keys"]
                if (solKeys?.isJsonArray == true) {
                    val sol = solKeys.asJsonArray.firstOrNull()
                    weatherData = Gson().fromJson(sol, MarsWeatherServerResponseData::class.java)
                }

                if (weatherData != null) {
                    marsWeatherLiveData.value = LoadableData.Success(weatherData.at)
                } else {
                    marsWeatherLiveData.value =
                        LoadableData.Error(Throwable("no data for this day"))
                }

            } else {
                marsWeatherLiveData.value =
                    LoadableData.Error(Throwable("mars weather is not success or body is null"))
            }
        }

        override fun onFailure(call: Call<Map<String, JsonElement>>, t: Throwable) {
            marsWeatherLiveData.value =
                LoadableData.Error(Throwable("mars weather is failure: " + t.message))
        }
    }
}