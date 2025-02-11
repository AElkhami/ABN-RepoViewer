package com.elkhami.core.domain.util

sealed interface DataError: Error {
    enum class Network: DataError {
        REQUEST_TIMEOUT,
        NO_INTERNET,
        SERVER_ERROR,
        SERIALIZATION,
        UNKNOWN
    }
}