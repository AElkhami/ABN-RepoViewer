package com.elkhami.repoviewer.presentation.repolist

data class RepoListState(
    val repoList: List<TempObject>? = null
)

data class TempObject(
    var name: String? = null,
    var imageUrl: String? = null,
    val isPrivate: String? = null,
    val visibility: String? = null
)
