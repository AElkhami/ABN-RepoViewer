package com.elkhami.core.domain.util

sealed class DataException : Throwable() {
    class RequestTimeoutException : DataException()
    class NoInternetException : DataException()
    class ServerErrorException : DataException()
    class SerializationException : DataException()
    class UnknownException : DataException()
}