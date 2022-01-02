package pro.fateeva.spacechucha.repository

import com.google.gson.annotations.SerializedName

data class MarsServerResponseData(
    @field:SerializedName("img_src") val imgSrc: String?,
    @field:SerializedName("earth_date") val earth_date: String?,
)
