package com.elkhami.repoviewer.data

import com.elkhami.core.data.networking.get
import com.elkhami.domain.util.DataError
import com.elkhami.domain.util.Result
import com.elkhami.repoviewer.domain.GitRepoModel
import com.elkhami.repoviewer.domain.RepoListRepository
import io.ktor.client.HttpClient

const val PER_PAGE = 10

class RepoListRepositoryImpl(
    private val httpClient: HttpClient,
) : RepoListRepository {
    override suspend fun getGitRepos(page: Int): Result<GitRepoModel, DataError.Network> {
        return httpClient.get<GitRepoModel>(
            route = "/users/abnamrocoesd/repos",
            queryParameters = mapOf("page" to page, "per_page" to PER_PAGE)
        )
    }
}