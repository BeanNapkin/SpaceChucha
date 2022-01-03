package pro.fateeva.spacechucha.repository

import com.google.gson.annotations.SerializedName

data class AsteroidsResponseData(
    @field:SerializedName("element_count") val asteroidsCount: String?,
)
