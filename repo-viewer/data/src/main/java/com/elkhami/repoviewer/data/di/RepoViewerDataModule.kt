package com.elkhami.repoviewer.data.di

import com.elkhami.repoviewer.data.RepoListRepositoryImpl
import com.elkhami.repoviewer.domain.RepoListRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repoViewerDataModule = module {
    singleOf(::RepoListRepositoryImpl).bind<RepoListRepository>()
}