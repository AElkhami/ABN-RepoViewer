package com.elkhami.repoviewer.presentation.repolist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.elkhami.core.presentation.components.RepoItem
import com.elkhami.core.presentation.designsystem.LocalDimensions
import com.elkhami.core.presentation.designsystem.LocalPadding
import com.elkhami.core.presentation.designsystem.Padding
import com.elkhami.core.presentation.extentions.rememberLazyListState
import com.elkhami.repoviewer.presentation.R
import com.elkhami.repoviewer.presentation.destinations.RepoDetailsScreenRootDestination
import com.elkhami.repoviewer.presentation.model.GitRepoUiModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.koin.androidx.compose.koinViewModel

@RootNavGraph(start = true)
@Destination
@Composable
fun RepoListScreenRoot(
    viewModel: RepoListViewModel = koinViewModel(),
    navigator: DestinationsNavigator
) {
    RepoListScreen(
        pagingData = viewModel.gitRepoPagingFlow,
        onAction = { actions ->
            when (actions) {
                is RepoListAction.onRepoClick -> {
                    navigator.navigate(
                        RepoDetailsScreenRootDestination(gitRepo = actions.gitRepoModel)
                    )
                }
            }
        }
    )
}

@Composable
private fun RepoListScreen(
    pagingData: Flow<PagingData<GitRepoUiModel>>,
    onAction: (RepoListAction) -> Unit
) {
    val repoItems = pagingData.collectAsLazyPagingItems()

    val snackbarHostState = remember { SnackbarHostState() }

    val networkError = stringResource(R.string.network_error)

    LaunchedEffect(key1 = repoItems.loadState.mediator) {
        if (repoItems.loadState.mediator?.refresh is LoadState.Error) {
            snackbarHostState.showSnackbar(
                networkError
            )
        }
    }

    val padding = LocalPadding.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBarComposable(
                padding = padding,
                name = stringResource(R.string.app_title)
            )
        },
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
            if (repoItems.loadState.mediator?.refresh is LoadState.Loading) {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.padding(padding.tinyPadding))
                Text(stringResource(R.string.loading), color = MaterialTheme.colorScheme.primary)
            } else {
                RepoListComposable(repoItems, onAction, padding)
            }
        }
    }
}

@Composable
fun RepoListComposable(
    repoItems: LazyPagingItems<GitRepoUiModel>,
    onAction: (RepoListAction) -> Unit,
    padding: Padding
) {
    val dimensions = LocalDimensions.current
    val listState = repoItems.rememberLazyListState()

    LazyColumn(
        modifier = Modifier,
        state = listState
    ) {
        items(
            count = repoItems.itemCount
        ) { index ->
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
                if (index < repoItems.itemCount - 1) {
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

@Composable
private fun TopBarComposable(
    modifier: Modifier = Modifier,
    padding: Padding,
    name: String,
) {
    val dimensions = LocalDimensions.current
    Box(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .height(dimensions.topBarHeight)
            .padding(horizontal = padding.mediumPadding)
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview
@Composable
private fun RepoListScreenPreview() {
    val repos = listOf(
        GitRepoUiModel(1, "Repo 1", "Description for repo 1"),
        GitRepoUiModel(2, "Repo 2", "Description for repo 2"),
        GitRepoUiModel(3, "Repo 3", "Description for repo 3")
    )

    val pagingData = flowOf(PagingData.from(repos))

    RepoListScreen(
        pagingData = pagingData,
        onAction = {}
    )
}