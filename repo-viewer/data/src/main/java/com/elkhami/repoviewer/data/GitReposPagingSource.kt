package com.elkhami.repoviewer.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.elkhami.domain.util.Result
import com.elkhami.repoviewer.domain.GitRepoModel

class GitReposPagingSource(
    private val remoteDataSource: GitReposDataSource
) : PagingSource<Int, GitRepoModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GitRepoModel> {
        val currentPage = params.key ?: 1
        val perPage = params.loadSize
        return when (val response =
            remoteDataSource.getGitRepos(page = currentPage, perPage = perPage)) {
            is Result.Error -> LoadResult.Error(response.error as Throwable)
            is Result.Success -> {
                LoadResult.Page(
                    data = response.data.map { gitRepoResponses ->
                        gitRepoResponses.toGitRepoModel()
                    },
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (response.data.isEmpty()) null else currentPage + 1
                )
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GitRepoModel>): Int? {
        return state.anchorPosition
    }
}