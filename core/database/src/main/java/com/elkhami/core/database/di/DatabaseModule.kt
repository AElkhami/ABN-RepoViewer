package com.elkhami.core.database.di

import androidx.room.Room
import com.elkhami.core.database.AbnRepoDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            AbnRepoDatabase::class.java,
            "abn_repo.db"
        ).build()
    }
    single { get<AbnRepoDatabase>().gitRepoDao }
}