package pro.fateeva.spacechucha.repository

import com.google.gson.annotations.SerializedName

data class MarsServerResponseData(
    @field:SerializedName("img_src") val image: String,
    @field:SerializedName("earth_date") val date: String
)
