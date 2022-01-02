package pro.fateeva.spacechucha.repository

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitImpl {
    private val baseUrl = "https://api.nasa.gov/"

    fun getRetrofitImpl(): RetrofitAPI {
        val retrofit = Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
        return retrofit.create(RetrofitAPI::class.java)
    }

}