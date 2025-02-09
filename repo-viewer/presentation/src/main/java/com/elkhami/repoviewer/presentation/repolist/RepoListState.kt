package com.elkhami.repoviewer.presentation.repolist

import com.elkhami.repoviewer.domain.GitRepoModel

data class RepoListState(
    val repoList: List<GitRepoModel>? = null,
    val isLoading: Boolean = false
)
