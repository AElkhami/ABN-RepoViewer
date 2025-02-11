package com.elkhami.core.domain.util

sealed interface Result<out D, out H> {
    data class Success<out D, out H>(val data: D, val header: H) : Result<D, H>
    data class Error<out H>(val error: Throwable) : Result<Nothing, H>
}