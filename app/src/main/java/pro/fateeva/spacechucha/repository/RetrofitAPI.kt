package pro.fateeva.spacechucha.repository

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitAPI {

    @GET("planetary/apod")
    fun getPictureOfTheDay(@Query("api_key") apiKey: String): Call<PictureOfTheDayResponseData>

    @GET("planetary/apod")
    fun getPictureOfTheDay(@Query("api_key") apiKey:String, @Query("date") date:String):Call<PictureOfTheDayResponseData>

    @GET("EPIC/api/natural")
    fun getEPIC(@Query("api_key") apiKey: String): Call<List<EarthEpicServerResponseData>>

    @GET("EPIC/api/natural/date/{date}")
    fun getEPICByDate(@Path("date") date:String, @Query("api_key") apiKey: String): Call<List<EarthEpicServerResponseData>>

    fun getMarsImageByDate(
        @Query("earth_date") earth_date: String,
        @Query("api_key") apiKey: String,
    ): Call<MarsPhotosServerResponseData>
}