package pro.fateeva.spacechucha.viewmodel

sealed class LoadableData<T> {
    data class Success<T>(val data: T) : LoadableData<T>()
    data class Loading<T>(val progress: Int?) : LoadableData<T>()
    data class Error<T>(val error: Throwable) : LoadableData<T>()
}