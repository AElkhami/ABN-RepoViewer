package com.elkhami.repoviewer.presentation.repolist

import com.elkhami.repoviewer.presentation.model.GitRepoUiModel

sealed interface RepoListAction {
    data class OnRepoClick(val gitRepoModel: GitRepoUiModel): RepoListAction
}
