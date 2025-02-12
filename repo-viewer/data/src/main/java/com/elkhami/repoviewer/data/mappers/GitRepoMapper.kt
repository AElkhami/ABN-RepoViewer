package com.elkhami.repoviewer.data.mappers

import com.elkhami.core.database.entity.GitRepoEntity
import com.elkhami.repoviewer.data.remote.GitRepoResponse

fun GitRepoResponse.toGitRepoEntity() = GitRepoEntity(
    id = 0,
    repoId = this.repoId ?: 0,
    name = this.name.orEmpty(),
    fullName = this.fullName.orEmpty(),
    description = this.description.orEmpty(),
    isPrivate = this.isPrivate,
    ownerAvatarUrl = this.ownerAvatarUrl.orEmpty(),
    htmlUrl = this.htmlUrl.orEmpty(),
    visibility = this.visibility.orEmpty()
)
