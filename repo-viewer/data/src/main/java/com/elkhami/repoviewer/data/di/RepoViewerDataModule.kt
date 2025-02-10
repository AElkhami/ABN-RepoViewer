package com.elkhami.repoviewer.data.di

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.elkhami.repoviewer.data.GitReposDataSource
import com.elkhami.repoviewer.data.GitReposPagingSource
import com.elkhami.repoviewer.data.PagingConstants.DEFAULT_PAGE_SIZE
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repoViewerDataModule = module {
    singleOf(::GitReposDataSource)
    singleOf(::GitReposPagingSource)
    single {
        Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { get<GitReposPagingSource>() }
        )
    }
}