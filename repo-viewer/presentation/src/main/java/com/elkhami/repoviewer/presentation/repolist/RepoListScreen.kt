package com.elkhami.repoviewer.presentation.repolist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.elkhami.core.presentation.components.RepoItem
import com.elkhami.core.presentation.designsystem.LocalDimensions
import com.elkhami.core.presentation.designsystem.LocalPadding
import com.elkhami.repoviewer.domain.GitRepoModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.koin.androidx.compose.koinViewModel

@Composable
fun RepoListScreenRoot(
    viewModel: RepoListViewModel = koinViewModel(),
    onRepoClick: (gitRepoModel: GitRepoModel) -> Unit
) {
    RepoListScreen(
        pagingData = viewModel.gitRepoPagingFlow,
        onAction = { actions ->
            when (actions) {
                is RepoListAction.onRepoClick -> {
                    onRepoClick(actions.gitRepoModel)
                }
            }
        }
    )
}

@Composable
private fun RepoListScreen(
    pagingData: Flow<PagingData<GitRepoModel>>,
    onAction: (RepoListAction) -> Unit
) {
    val repoItems = pagingData.collectAsLazyPagingItems()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = repoItems.loadState) {
        if (repoItems.loadState.refresh is LoadState.Error) {
            snackbarHostState.showSnackbar(
                "Error: " + (repoItems.loadState.refresh as LoadState.Error).error.message
            )
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (repoItems.loadState.refresh is LoadState.Loading) {
                CircularProgressIndicator()
            } else {
                RepoListComposable(repoItems, onAction)
            }
        }
    }
}

@Composable
fun RepoListComposable(
    repoItems: LazyPagingItems<GitRepoModel>,
    onAction: (RepoListAction) -> Unit
) {
    val padding = LocalPadding.current
    val dimensions = LocalDimensions.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding.mediumPadding)
    ) {
        items(repoItems.itemCount) { index ->
            repoItems[index]?.let { repoItem ->
                RepoItem(
                    modifier = Modifier.clickable {
                        onAction(RepoListAction.onRepoClick(repoItem))
                    },
                    name = repoItem.name ?: "",
                    imageUrl = repoItem.ownerAvatarUrl ?: "",
                    isPrivate = repoItem.isPrivate.toString(),
                    visibility = repoItem.visibility ?: ""
                )
                if (index < repoItems.itemCount) {
                    HorizontalDivider(
                        modifier = Modifier.padding(
                            start = padding.mediumPadding,
                            end = padding.mediumPadding
                        ),
                        color = Color.Gray,
                        thickness = dimensions.dividerThickness
                    )
                }
            }
        }
        item {
            if (repoItems.loadState.append is LoadState.Loading) {
                Box(modifier = Modifier.fillParentMaxWidth()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
}

@Preview
@Composable
private fun RepoListScreenPreview() {
    val repos = listOf(
        GitRepoModel(1, "Repo 1", "Description for repo 1"),
        GitRepoModel(2, "Repo 2", "Description for repo 2"),
        GitRepoModel(3, "Repo 3", "Description for repo 3")
    )

    val pagingData = flowOf(PagingData.from(repos))

    RepoListScreen(
        pagingData = pagingData,
        onAction = {}
    )
}