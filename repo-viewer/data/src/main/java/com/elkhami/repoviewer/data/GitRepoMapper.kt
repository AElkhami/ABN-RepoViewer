package com.elkhami.repoviewer.data

import com.elkhami.repoviewer.domain.GitRepoModel

fun GitRepoResponse.toGitRepoModel(): GitRepoModel {
    return GitRepoModel(
        id = this.id,
        name = this.name,
        fullName = this.fullName,
        description = this.description,
        isPrivate = this.isPrivate,
        ownerAvatarUrl = this.owner?.avatarUrl,
        htmlUrl = this.htmlUrl,
        visibility = this.visibility
    )
}