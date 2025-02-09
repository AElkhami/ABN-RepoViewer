package com.elkhami.repoviewer.data

import com.elkhami.core.data.networking.get
import com.elkhami.domain.util.DataError
import com.elkhami.domain.util.Result
import com.elkhami.domain.util.map
import com.elkhami.repoviewer.domain.GitRepoModel
import com.elkhami.repoviewer.domain.RepoListRepository
import io.ktor.client.HttpClient

const val PER_PAGE = 10
const val route = "/users/abnamrocoesd/repos"

class RepoListRepositoryImpl(
    private val httpClient: HttpClient,
) : RepoListRepository {
    override suspend fun getGitRepos(page: Int): Result<List<GitRepoModel>, DataError.Network> {
        return httpClient.get<List<GitRepoResponse>>(
            route = route,
            queryParameters = mapOf("page" to page, "per_page" to PER_PAGE)
        ).map { gitRepoResponses ->
            gitRepoResponses.map { it.toGitRepoModel() }
        }
    }
}