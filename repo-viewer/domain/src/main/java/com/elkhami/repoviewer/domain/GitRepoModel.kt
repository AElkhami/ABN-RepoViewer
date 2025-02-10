package com.elkhami.repoviewer.domain

data class GitRepoModel(
    val id: Int ? = null,
    val name: String ? = null,
    val fullName: String ? = null,
    val description: String ? = null,
    val isPrivate: Boolean = false,
    val ownerAvatarUrl: String ? = null,
    val htmlUrl: String ? = null,
    val visibility: String ? = null
)
