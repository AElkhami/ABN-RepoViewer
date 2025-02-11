package com.elkhami.core.domain.util

sealed interface Result<out D, out H> {
    data class Success<out D, out H>(val data: D, val header: H) : Result<D, H>
    data class Error<out H>(val error: Throwable, val header: H) : Result<Nothing, H>
}

inline fun <T, H, R> Result<T, H>.map(map: (T) -> R): Result<R, H> {
    return when (this) {
        is Result.Error -> Result.Error(error, header)
        is Result.Success -> Result.Success(map(data), header)
    }
}