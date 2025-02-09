package com.elkhami.repoviewer.presentation.repolist

import com.elkhami.repoviewer.domain.GitRepoModel

sealed interface RepoListAction {
    data class onRepoClick(val gitRepoModel: GitRepoModel): RepoListAction
}
