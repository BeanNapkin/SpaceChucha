package pro.fateeva.spacechucha.viewmodel

import pro.fateeva.spacechucha.repository.EarthEpicServerResponseData
import pro.fateeva.spacechucha.repository.PictureOfTheDayResponseData

sealed class AppState{
    data class SuccessPictureOfTheDay(val pictureOfTheDayResponseData: PictureOfTheDayResponseData):AppState()
    data class SuccessEarthEpic(val earthEpicServerResponseData: List<EarthEpicServerResponseData>):AppState()
    data class Loading(val progress: Int?):AppState()
    data class Error(val error: Throwable):AppState()
}
