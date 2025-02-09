package com.elkhami.repoviewer.presentation.repolist

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import com.elkhami.core.presentation.ui.asUiText
import com.elkhami.domain.util.DataError
import com.elkhami.repoviewer.domain.GitRepoModel
import com.elkhami.repoviewer.domain.RepoListRepository
import io.mockk.coEvery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import com.elkhami.domain.util.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.UnconfinedTestDispatcher

@OptIn(ExperimentalCoroutinesApi::class)
class RepoListViewModelTest{

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var repository : RepoListRepository
    private lateinit var viewModel : RepoListViewModel

    private val page = 1

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk<RepoListRepository>()
        viewModel = RepoListViewModel(repository)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getRepoList should update state with repo list on success`() = runTest {

        val repoList = listOf(
            GitRepoModel(id = 1, name = "repo1"),
            GitRepoModel(id = 2, name = "repo2")
        )

        coEvery { repository.getGitRepos(page) } returns Result.Success(repoList)

        viewModel.getRepoList(page)
        advanceUntilIdle()

        assertThat(viewModel.state.repoList).isEqualTo(repoList)
    }

    @Test
    fun `getRepoList should send error event on error`() = runTest {
        val dataError = DataError.Network.UNKNOWN
        coEvery { repository.getGitRepos(page) } returns Result.Error(dataError)

        val expectedErrorMessage = dataError

        val job = launch { viewModel.getRepoList(page) }

        val event = viewModel.events.first()

        assertThat(event).isInstanceOf(RepoListEvent.Error::class)

        if (event is RepoListEvent.Error) {
            assertThat(event.error == expectedErrorMessage.asUiText())
        }

        job.cancel()
        advanceUntilIdle()
    }

    @Test
    fun `getRepoList should set is loading false when done`() = runTest {
        val repoList = listOf(
            GitRepoModel(id = 1, name = "repo1"),
            GitRepoModel(id = 2, name = "repo2")
        )

        coEvery { repository.getGitRepos(page) } returns Result.Success(repoList)

        assertThat(viewModel.state.isLoading).isEqualTo(false)
    }


    @Test
    fun `initial state is correct`() {
        assertThat(viewModel.state).isEqualTo(RepoListState())
    }
}