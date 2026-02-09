package com.lestec.pexels

import android.app.Application
import com.lestec.pexels.data.FileDownloaderImpl
import com.lestec.pexels.data.HttpRepoImpl
import com.lestec.pexels.data.KtorClientProvider
import com.lestec.pexels.data.LocalRepoImpl
import com.lestec.pexels.data.RepoImpl
import com.lestec.pexels.data.getRoomClient
import com.lestec.pexels.domain.FileDownloader
import com.lestec.pexels.domain.HttpRepo
import com.lestec.pexels.domain.LocalRepo
import com.lestec.pexels.domain.Repo
import com.lestec.pexels.ui.screenBookmarks.BookmarksViewModel
import com.lestec.pexels.ui.screenDetails.DetailsViewModel
import com.lestec.pexels.ui.screenHome.HomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()
        val context = applicationContext
        startKoin {
            androidLogger()
            androidContext(this@MainApp)
            modules(
                module {
                    single { HttpRepoImpl(KtorClientProvider.client) } bind HttpRepo::class
                    single { LocalRepoImpl(getRoomClient(context)) } bind LocalRepo::class
                    single { FileDownloaderImpl(context) } bind FileDownloader::class
                    single { RepoImpl(http = get(), local = get(), files = get()) } bind Repo::class
                    viewModel { HomeViewModel(get()) }
                    viewModel { DetailsViewModel(get()) }
                    viewModel { BookmarksViewModel(get()) }
                }
            )
        }
    }
}