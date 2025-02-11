package com.elkhami.repoviewer.data

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
import io.ktor.http.HttpHeaders
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class GitReposRemoteMediator(
    private val remoteDataSource: GitReposDataSource,
    private val gitRepoDatabase: AbnRepoDatabase
) : RemoteMediator<Int, GitRepoEntity>() {
    private var maxPage = 1
    private var currentPage: Int = 1
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
                        //Display the cached data
                        MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    }

                    is Result.Success -> {

                        if (loadType == LoadType.REFRESH) {
                            gitRepoDatabase.gitRepoDao.clearAll()
                        }

                        val repos = response.data
                        val linkHeader = response.header[HttpHeaders.Link]

                        maxPage = extractLastPageNumber(linkHeader) ?: 1

                        val entities = repos.map {
                            it.toGitRepoEntity()
                        }
                        gitRepoDatabase.gitRepoDao.insertAll(entities)

                        currentPage = page

                        val endOfPaginationReached = repos.isEmpty() || currentPage >= maxPage
                        MediatorResult.Success(
                            endOfPaginationReached = endOfPaginationReached
                        )
                    }
                }
            }
        } catch (e: IOException) {
            MediatorResult.Error(e)
        }
    }
}