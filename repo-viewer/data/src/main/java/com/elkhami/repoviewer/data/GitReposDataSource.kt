package com.elkhami.repoviewer.data

import com.elkhami.core.data.networking.getForPaging
import com.elkhami.domain.util.Result
import com.elkhami.domain.util.map
import com.elkhami.repoviewer.domain.GitRepoModel
import io.ktor.client.HttpClient

class GitReposDataSource(private val httpClient: HttpClient) {
    suspend fun getGitRepos(page: Int, perPage: Int): Result<List<GitRepoResponse>, Any> {
        return httpClient.getForPaging<List<GitRepoResponse>>(
            route = ROUTE,
            queryParameters = mapOf("page" to page, "per_page" to perPage)
        )
    }

    companion object {
        const val ROUTE = "/users/abnamrocoesd/repos"
    }
}