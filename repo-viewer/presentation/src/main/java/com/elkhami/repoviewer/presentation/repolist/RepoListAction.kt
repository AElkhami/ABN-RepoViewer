package com.elkhami.repoviewer.presentation.repolist

sealed interface RepoListAction {
    data class onRepoClick(val tempObject: TempObject): RepoListAction
}
