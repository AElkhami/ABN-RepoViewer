package com.elkhami.repoviewer.domain

import com.elkhami.domain.util.DataError
import com.elkhami.domain.util.Result

interface RepoListRepository {
    suspend fun getGitRepos(page: Int): Result<List<GitRepoModel>, DataError.Network>
}