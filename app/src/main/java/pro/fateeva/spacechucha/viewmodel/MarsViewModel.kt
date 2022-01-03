package pro.fateeva.spacechucha.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pro.fateeva.spacechucha.BuildConfig
import pro.fateeva.spacechucha.repository.EarthEpicServerResponseData
import pro.fateeva.spacechucha.repository.MarsPhotosServerResponseData
import pro.fateeva.spacechucha.repository.RetrofitImpl
import pro.fateeva.spacechucha.viewmodel.LoadableData.Success
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException

class MarsViewModel(
    private val retrofitImpl: RetrofitImpl = RetrofitImpl()
) : ViewModel() {
    private val marsPhotoLiveData: MutableLiveData<LoadableData<MarsPhotosServerResponseData>> = MutableLiveData()

    fun getMarsPhotosData() = marsPhotoLiveData

    fun getMarsPhotos(date: String) {
        marsPhotoLiveData.value = LoadableData.Loading(0)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            marsPhotoLiveData.value = LoadableData.Error(Throwable("wrong key"))
        } else {
            retrofitImpl.getRetrofitImpl().getMarsImageByDate(date, apiKey).enqueue(callMarsPhoto)
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
                marsPhotoLiveData.value = LoadableData.Error(Throwable("mars photos is not success or body is null"))
            }
        }

        override fun onFailure(call: Call<MarsPhotosServerResponseData>, t: Throwable) {
            marsPhotoLiveData.value = LoadableData.Error(Throwable("mars photos is failure: " + t.message))
        }
    }

}