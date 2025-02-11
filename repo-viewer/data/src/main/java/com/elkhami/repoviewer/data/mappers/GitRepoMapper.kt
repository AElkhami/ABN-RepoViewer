package com.elkhami.repoviewer.data.mappers

import com.elkhami.core.database.entity.GitRepoEntity
import com.elkhami.repoviewer.data.GitRepoResponse

fun GitRepoResponse.toGitRepoEntity() = GitRepoEntity(
    repoId = this.repoId ?: 0,
    name = this.name ?: "",
    fullName = this.fullName ?: "",
    description = this.description ?: "",
    isPrivate = this.isPrivate,
    ownerAvatarUrl = this.ownerAvatarUrl ?: "",
    htmlUrl = this.htmlUrl ?: "",
    visibility = this.visibility ?: ""
)
