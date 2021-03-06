package pro.fateeva.spacechucha.repository

import com.google.gson.JsonElement
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

    @GET ("neo/rest/v1/feed")
    fun getDataAboutAsteroidsByDate(@Query("start_date") date:String, @Query("api_key") apiKey: String): Call<AsteroidsResponseData>

    @GET("/mars-photos/api/v1/rovers/curiosity/photos")
    fun getMarsImageByDate(@Query("earth_date") earth_date: String, @Query("api_key") apiKey: String): Call<MarsPhotosServerResponseData>

    @GET ("insight_weather/")
    fun getMarsWeather(@Query("api_key") apiKey: String, @Query("ver") version: String = "1.0", @Query("feedtype") type: String = "json"): Call<Map<String, JsonElement>>

}