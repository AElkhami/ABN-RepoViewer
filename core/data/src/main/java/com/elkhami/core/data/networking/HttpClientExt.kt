package com.elkhami.core.data.networking

import com.elkhami.core.data.BuildConfig
import com.elkhami.core.domain.util.DataException
import com.elkhami.core.domain.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.Headers
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.SerializationException
import java.io.IOException

suspend inline fun <reified Response : Any> HttpClient.get(
    route: String,
    queryParameters: Map<String, Any?> = mapOf()
): Result<Response, Headers> {
    return safeCall {
        get {
            url(constructRoute(route))
            queryParameters.forEach { (key, value) ->
                parameter(key, value)
            }
        }
    }
}

suspend inline fun <reified T> HttpClient.safeCall(execute: () -> HttpResponse): Result<T, Headers> {
    val response: HttpResponse = try {
        execute()
    } catch (e: UnresolvedAddressException) {
        e.printStackTrace()
        return Result.Error(DataException.NoInternetException(), Headers.Empty)
    } catch (e: SerializationException) {
        e.printStackTrace()
        return Result.Error(DataException.SerializationException(), Headers.Empty)
    } catch (e: IOException) {
        e.printStackTrace()
        return Result.Error(DataException.NoInternetException(), Headers.Empty)
    } catch (e: Exception) {
        if (e is CancellationException) throw e
        e.printStackTrace()
        return Result.Error(DataException.UnknownException(), Headers.Empty)
    }

    return responseToDataResult(response)
}


suspend inline fun <reified T> HttpClient.responseToDataResult(response: HttpResponse): Result<T, Headers> {
    return when (response.status.value) {
        in 200..299 -> Result.Success(response.body<T>(), response.headers)
        408 -> Result.Error(DataException.RequestTimeoutException(), response.headers)
        in 500..599 -> Result.Error(DataException.ServerErrorException(), response.headers)
        else -> Result.Error(DataException.UnknownException(), response.headers)
    }
}

fun constructRoute(route: String): String {
    return when {
        route.contains(BuildConfig.BASE_URL) -> route
        route.startsWith("/") -> BuildConfig.BASE_URL + route
        else -> BuildConfig.BASE_URL + "/$route"
    }
}