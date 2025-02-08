package com.elkhami.repoviewer.presentation.repolist

data class RepoListState(
    var ownerName: String? = null,
    var ownerImage: String? = null,
    val isPrivate: Boolean = false,
    val visibility: String? = null
)
