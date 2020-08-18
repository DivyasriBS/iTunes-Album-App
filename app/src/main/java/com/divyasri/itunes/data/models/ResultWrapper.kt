package com.divyasri.itunes.data.models

  open class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class GenericError(val code: Int? = null, val error: ErrorResponse? = null) : ResultWrapper<Nothing>()

    data class NetworkError(val exception: Throwable, val message: String = exception.localizedMessage) :
            ResultWrapper<Nothing>()
}

class ErrorResponse()
