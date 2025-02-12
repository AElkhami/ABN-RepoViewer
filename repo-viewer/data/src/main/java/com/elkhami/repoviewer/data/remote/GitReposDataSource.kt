package com.elkhami.repoviewer.data.remote

import com.elkhami.core.data.networking.get
import com.elkhami.core.domain.util.Result
import io.ktor.client.HttpClient
import io.ktor.http.Headers

class GitReposDataSource(private val httpClient: HttpClient) {
    suspend fun getGitRepos(page: Int, perPage: Int): Result<List<GitRepoResponse>, Headers> {
        return httpClient.get<List<GitRepoResponse>>(
            route = ROUTE,
            queryParameters = mapOf("page" to page, "per_page" to perPage)
        )
    }

    companion object {
        const val ROUTE = "/users/abnamrocoesd/repos"
    }
}