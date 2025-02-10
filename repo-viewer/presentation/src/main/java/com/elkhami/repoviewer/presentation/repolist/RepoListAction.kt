package com.elkhami.repoviewer.presentation.repolist

import com.elkhami.repoviewer.presentation.mode.GitRepoUiModel

sealed interface RepoListAction {
    data class onRepoClick(val gitRepoModel: GitRepoUiModel): RepoListAction
}
