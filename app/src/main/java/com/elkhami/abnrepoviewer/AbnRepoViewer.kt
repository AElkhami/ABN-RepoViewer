package com.elkhami.abnrepoviewer

import android.app.Application
import com.elkhami.core.data.di.coreDataModule
import com.elkhami.repoviewer.data.di.repoViewerDataModule
import com.elkhami.repoviewer.presentation.di.repoViewerViewModelModule
import timber.log.Timber
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AbnRepoViewer: Application() {
    override fun onCreate() {
        super.onCreate()

        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@AbnRepoViewer)
            modules(
                coreDataModule,
                repoViewerViewModelModule,
                repoViewerDataModule
            )
        }
    }
}