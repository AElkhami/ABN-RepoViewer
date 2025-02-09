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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach

const val PER_PAGE = 10

class RepoListRepositoryImplTest {
    private val httpClient: HttpClient = mockk(relaxed = true)
    private val repoListRepository = RepoListRepositoryImpl(httpClient)

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getGitRepos should return success result when httpClient returns success`() = runTest {
        val page = 1
        val expectedResult: Result<GitRepoModel, DataError.Network> = Result.Success(GitRepoModel())
        coEvery {
            httpClient.get<GitRepoModel>(
                route = "/users/abnamrocoesd/repos",
                queryParameters = mapOf(
                    "page" to page,
                    "per_page" to PER_PAGE
                )
            )
        } returns expectedResult

        val result = repoListRepository.getGitRepos(page)

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `getGitRepos should return network error result when httpClient returns failure`() =
        runTest {
            val page = 1
            val expectedError = DataError.Network.UNKNOWN
            coEvery {
                httpClient.get<GitRepoModel>(
                    route = "/users/abnamrocoesd/repos",
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