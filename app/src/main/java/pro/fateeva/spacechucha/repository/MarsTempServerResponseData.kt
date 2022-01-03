package pro.fateeva.spacechucha.repository

import com.google.gson.annotations.SerializedName

data class MarsTempServerResponseData(
    @field:SerializedName("mn")val min: String,
    @field:SerializedName("mx")val max: String
)

data class MarsWeatherServerResponseData(
    @field:SerializedName("AT")val at: MarsTempServerResponseData
)
