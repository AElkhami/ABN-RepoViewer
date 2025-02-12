package com.elkhami.repoviewer.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.elkhami.core.data.networking.extractLastPageNumber
import com.elkhami.core.database.AbnRepoDatabase
import com.elkhami.core.database.entity.GitRepoEntity
import com.elkhami.core.domain.util.Result
import com.elkhami.repoviewer.data.mappers.toGitRepoEntity
import com.elkhami.repoviewer.data.paging.PagingConstants.CURRENT_PAGE
import com.elkhami.repoviewer.data.paging.PagingConstants.MAX_PAGE
import com.elkhami.repoviewer.data.remote.GitRepoResponse
import com.elkhami.repoviewer.data.remote.GitReposDataSource
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class GitReposRemoteMediator(
    private val remoteDataSource: GitReposDataSource,
    private val gitRepoDatabase: AbnRepoDatabase
) : RemoteMediator<Int, GitRepoEntity>() {

    private val gitRepoDao = gitRepoDatabase.gitRepoDao

    private var maxPage = MAX_PAGE
    private var currentPage: Int = CURRENT_PAGE

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, GitRepoEntity>
    ): MediatorResult {

        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    currentPage = 1
                    1
                }

                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val nextPage = currentPage + 1
                    if (nextPage > maxPage) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }
                    nextPage
                }
            }

            val response = remoteDataSource.getGitRepos(
                page = page,
                perPage = state.config.pageSize
            )

            gitRepoDatabase.withTransaction {
                when (response) {
                    is Result.Error -> {
                        handleFail(response)
                    }

                    is Result.Success -> {
                        handleSuccess(
                            response = response,
                            loadType = loadType,
                            page = page
                        )
                    }
                }
            }
        } catch (e: IOException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun handleSuccess(
        loadType: LoadType,
        response: Result.Success<List<GitRepoResponse>, Headers>,
        page: Int
    ): MediatorResult {
        if (loadType == LoadType.REFRESH) {
            gitRepoDao.clearAll()
        }

        val repos = response.data
        val linkHeader = response.header[HttpHeaders.Link]

        maxPage = extractLastPageNumber(linkHeader) ?: 1

        val entities = repos.map {
            it.toGitRepoEntity()
        }
        gitRepoDao.insertAll(entities)

        currentPage = page

        val endOfPaginationReached = repos.isEmpty() || currentPage >= maxPage
        return MediatorResult.Success(
            endOfPaginationReached = endOfPaginationReached
        )
    }

    private suspend fun handleFail(response: Result.Error<Headers>): MediatorResult {
        val count = gitRepoDao.count()
        return if (count == 0) {
            MediatorResult.Error(response.error)
        } else {
            MediatorResult.Success(endOfPaginationReached = true)
        }
    }
}