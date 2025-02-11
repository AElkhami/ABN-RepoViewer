package com.elkhami.core.presentation.ui

import com.elkhami.core.domain.util.DataException

fun Throwable.asUiText(): UiText {
    return when (this) {
        is DataException.RequestTimeoutException -> UiText.StringResource(R.string.error_request_timeout)
        is DataException.NoInternetException -> UiText.StringResource(R.string.error_no_internet)
        is DataException.ServerErrorException -> UiText.StringResource(R.string.error_server_error)
        is DataException.SerializationException -> UiText.StringResource(R.string.error_serialization)
        else -> UiText.StringResource(R.string.error_unknown)
    }
}