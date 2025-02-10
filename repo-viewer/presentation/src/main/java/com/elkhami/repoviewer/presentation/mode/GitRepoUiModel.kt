package com.elkhami.repoviewer.presentation.mode

import com.elkhami.repoviewer.domain.GitRepoModel
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

fun GitRepoModel.toGitRepoUiModel() = GitRepoUiModel(
    id = this.id,
    name = this.name,
    fullName = this.fullName,
    description = this.description,
    isPrivate = this.isPrivate,
    ownerAvatarUrl = this.ownerAvatarUrl,
    htmlUrl = this.htmlUrl,
    visibility = this.visibility
)