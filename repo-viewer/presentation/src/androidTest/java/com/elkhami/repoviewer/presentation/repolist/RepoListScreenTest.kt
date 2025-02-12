package com.elkhami.repoviewer.presentation.repolist

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import com.elkhami.repoviewer.presentation.model.GitRepoUiModel
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

class RepoListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun repoListScreen_repoList_displaysRepoItemsAndHandlesClick() {
        val repoItem1 = GitRepoUiModel(name = "Repo 1", ownerAvatarUrl = "url1", isPrivate = false, visibility = "public")
        val repoItem2 = GitRepoUiModel(name = "Repo 2", ownerAvatarUrl = "url2", isPrivate = true, visibility = "private")

        val pagingData = PagingData.from(listOf(repoItem1, repoItem2))
        val mockPagingData: Flow<PagingData<GitRepoUiModel>> = flowOf(pagingData)
        val mockRepoItems = mockk<LazyPagingItems<GitRepoUiModel>>(relaxed = true)
        every { mockRepoItems.itemCount } returns 2
        every { mockRepoItems[0] } returns repoItem1
        every { mockRepoItems[1] } returns repoItem2
        val onAction: (RepoListAction) -> Unit = mockk(relaxed = true)
        val loadStateNotLoading = LoadState.NotLoading(false)
        val loadStateMediator = mockk<LoadStates>(relaxed = true)

        every { mockRepoItems.loadState.mediator } returns loadStateMediator
        every { loadStateMediator.refresh } returns loadStateNotLoading

        composeTestRule.setContent {
            RepoListScreen(
                pagingData = mockPagingData,
                onAction = onAction
            )
        }

        composeTestRule.onNodeWithText("Repo 1").performClick()

        coVerify { onAction(RepoListAction.OnRepoClick(repoItem1)) }
        confirmVerified(onAction)

        composeTestRule.onNodeWithText("Repo 2").performClick()

        coVerify { onAction(RepoListAction.OnRepoClick(repoItem2)) }
        confirmVerified(onAction)
    }
}