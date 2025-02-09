package com.elkhami.repoviewer.presentation.repolist

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.elkhami.core.presentation.components.RepoItem
import com.elkhami.core.presentation.designsystem.AbnRepoViewerTheme
import com.elkhami.core.presentation.designsystem.LocalDimensions
import com.elkhami.core.presentation.designsystem.LocalPadding
import com.elkhami.core.presentation.ui.observeAsEvent
import com.elkhami.repoviewer.domain.GitRepoModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun RepoListScreenRoot(
    viewModel: RepoListViewModel = koinViewModel(),
    onRepoClick: (gitRepoModel: GitRepoModel) -> Unit
) {
    val context = LocalContext.current

    observeAsEvent(flow = viewModel.events) { event ->
        when (event) {
            is RepoListEvent.Error ->
                Toast.makeText(
                    context,
                    event.error.asString(context),
                    Toast.LENGTH_LONG
                ).show()
        }
    }

    LaunchedEffect(true) {
        viewModel.getRepoList(1)
    }

    RepoListScreen(
        state = viewModel.state,
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
    state: RepoListState,
    onAction: (RepoListAction) -> Unit,
) {
    val padding = LocalPadding.current
    val dimensions = LocalDimensions.current

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding.mediumPadding)
        ) {
            state.repoList?.let { list ->
                itemsIndexed(list) { index, item ->
                    RepoItem(
                        modifier = Modifier.clickable {
                            onAction(RepoListAction.onRepoClick(item))
                        },
                        name = item.name ?: "",
                        imageUrl = item.ownerAvatarUrl ?: "",
                        isPrivate = item.isPrivate.toString(),
                        visibility = item.visibility ?: ""
                    )
                    if (index < list.lastIndex) {
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
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RepoListScreenPreview() {
    AbnRepoViewerTheme {
        RepoListScreen(
            state = RepoListState(
                listOf(
                    GitRepoModel(
                        name = "terraform-aws-fargate",
                        isPrivate = false,
                        visibility = "public",
                        ownerAvatarUrl = ""
                    ),
                    GitRepoModel(
                        name = "terraform-aws-fargate",
                        isPrivate = false,
                        visibility = "public",
                        ownerAvatarUrl = ""
                    )
                )
            ),
            onAction = {}
        )
    }
}