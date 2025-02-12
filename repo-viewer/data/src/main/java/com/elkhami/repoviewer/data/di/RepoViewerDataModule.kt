package com.elkhami.repoviewer.data.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.elkhami.core.database.AbnRepoDatabase
import com.elkhami.repoviewer.data.remote.GitReposDataSource
import com.elkhami.repoviewer.data.paging.GitReposRemoteMediator
import com.elkhami.repoviewer.data.paging.PagingConstants.DEFAULT_PAGE_SIZE
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

@OptIn(ExperimentalPagingApi::class)
val repoViewerDataModule = module {
    singleOf(::GitReposDataSource)
    singleOf(::GitReposRemoteMediator)
    single {
        Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGE_SIZE,
                prefetchDistance = 5,
                initialLoadSize = DEFAULT_PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = GitReposRemoteMediator(get(), get()),
            pagingSourceFactory = {
                get<AbnRepoDatabase>().gitRepoDao.getGitRepoList()
            }
        )
    }
}