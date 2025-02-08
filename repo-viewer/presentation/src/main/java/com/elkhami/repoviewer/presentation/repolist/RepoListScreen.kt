package com.elkhami.repoviewer.presentation.repolist

import com.elkhami.core.presentation.designsystem.AbnRepoViewerTheme

@androidx.compose.runtime.Composable

fun RepoListScreenRoot(
    viewModel: RepoListViewModel = org.koin.androidx.compose.koinViewModel()
) {
    RepoListScreen(
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@androidx.compose.runtime.Composable

private fun RepoListScreen(
    state: RepoListState,
    onAction: (RepoListAction) -> Unit
) {

}

@androidx.compose.ui.tooling.preview.Preview

@androidx.compose.runtime.Composable

private fun RepoListScreenPreview() {

    AbnRepoViewerTheme {
        RepoListScreen(
            state = RepoListState(),
            onAction = {}
        )
    }
}