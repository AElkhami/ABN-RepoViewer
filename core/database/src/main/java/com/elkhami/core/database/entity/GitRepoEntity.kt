package com.elkhami.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "git_repo")
data class GitRepoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val repoId: Int,
    val name: String,
    val fullName: String,
    val description: String,
    val isPrivate: Boolean = false,
    val ownerAvatarUrl: String,
    val htmlUrl: String,
    val visibility: String
)
