package com.elkhami.repoviewer.presentation.mapper

import com.elkhami.core.database.entity.GitRepoEntity
import com.elkhami.repoviewer.presentation.model.GitRepoUiModel

fun GitRepoEntity.toGitRepoUiModel() = GitRepoUiModel(
    name = this.name,
    fullName = this.fullName,
    description = this.description,
    isPrivate = this.isPrivate,
    ownerAvatarUrl = this.ownerAvatarUrl,
    htmlUrl = this.htmlUrl,
    visibility = this.visibility
)