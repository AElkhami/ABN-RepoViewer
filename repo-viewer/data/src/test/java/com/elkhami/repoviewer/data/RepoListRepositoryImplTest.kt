package com.elkhami.repoviewer.data

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.elkhami.core.data.networking.get
import com.elkhami.domain.util.DataError
import io.ktor.client.HttpClient
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import com.elkhami.domain.util.Result
import com.elkhami.repoviewer.domain.GitRepoModel
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals

class RepoListRepositoryImplTest {
    private val httpClient: HttpClient = mockk(relaxed = true)
    private val repoListRepository = RepoListRepositoryImpl(httpClient)

    @Test
    fun `getGitRepos should return success result with mapped data when httpClient returns success`() = runTest {
        val page = 1

        val gitRepoResponseList = listOf(
            GitRepoResponse(id = 1, name = "repo1", fullName = "owner/repo1", owner = Owner(avatarUrl = "url1")),
            GitRepoResponse(id = 2, name = "repo2", fullName = "owner/repo2", owner = Owner(avatarUrl = "url2"))
        )
        val expectedGitRepoModelList = listOf(
            GitRepoModel(id = 1, name = "repo1", fullName = "owner/repo1", ownerAvatarUrl = "url1"),
            GitRepoModel(id = 2, name = "repo2", fullName = "owner/repo2", ownerAvatarUrl = "url2")
        )

        val successResult: Result<List<GitRepoResponse>, DataError.Network> = Result.Success(gitRepoResponseList)

        coEvery {
            httpClient.get<List<GitRepoResponse>>(
                route = route,
                queryParameters = mapOf("page" to page, "per_page" to PER_PAGE)
            )
        } returns successResult


        val result = repoListRepository.getGitRepos(page)

        Assertions.assertTrue(result is Result.Success)
        if (result is Result.Success) {
            assertEquals(expectedGitRepoModelList, result.data)
        }
    }

    @Test
    fun `getGitRepos should return network error result when httpClient returns failure`() =
        runTest {
            val page = 1
            val expectedError = DataError.Network.UNKNOWN
            coEvery {
                httpClient.get<List<GitRepoResponse>>(
                    route = route,
                    queryParameters = mapOf(
                        "page" to page,
                        "per_page" to PER_PAGE
                    )
                )
            } returns Result.Error(expectedError)

            val result = repoListRepository.getGitRepos(page)

            assertThat(result).isEqualTo(Result.Error(expectedError))
        }
}