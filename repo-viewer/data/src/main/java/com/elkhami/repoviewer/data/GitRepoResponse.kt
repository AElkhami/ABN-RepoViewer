package com.elkhami.repoviewer.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GitRepoModel(
    val id: Int? = null,

    val name: String? = null,

    @SerialName("full_name")
    val fullName: String? = null,

    val description: String? = null,

    @SerialName("private")
    val isPrivate: Boolean = false,

    @SerialName("owner")
    val owner: Owner? = null,

    @SerialName("html_url")
    val htmlUrl: String? = null,

    val visibility: String? = null
) {
    val ownerAvatarUrl: String?
        get() = owner?.avatarUrl
}

@Serializable
data class Owner(
    @SerialName("avatar_url")
    val avatarUrl: String? = null
)
