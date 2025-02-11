package com.elkhami.core.domain.util


sealed interface Result<out D, out H, out E> {
    data class Success<out D, out H>(val data: D, val header: H) : Result<D, H, Nothing>
    data class Error<out E, out H>(val error: E, val header: H) : Result<Nothing, H, E>
}

inline fun <T, H, E, R> Result<T, H, E>.map(map: (T) -> R): Result<R, H, E> {
    return when (this) {
        is Result.Error -> Result.Error(error, header)
        is Result.Success -> Result.Success(map(data), header)
    }
}

fun <T, H, E> Result<T, H, E>.asEmptyDataResult(): Result<Unit, H, E> {
    return map { }
}

typealias EmptyResult<E> = Result<Unit, Unit, E>