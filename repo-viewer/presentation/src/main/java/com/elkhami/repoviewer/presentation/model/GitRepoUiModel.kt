package com.elkhami.repoviewer.presentation.model

import kotlinx.serialization.Serializable

@Serializable
data class GitRepoUiModel(
    val id: Int? = null,
    val name: String? = null,
    val fullName: String? = null,
    val description: String? = null,
    val isPrivate: Boolean = false,
    val ownerAvatarUrl: String? = null,
    val htmlUrl: String? = null,
    val visibility: String? = null
)