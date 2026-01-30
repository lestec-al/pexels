package com.lestec.pexels

import android.app.Application
import com.lestec.pexels.data.HttpRepoImpl
import com.lestec.pexels.data.KtorClientProvider
import com.lestec.pexels.data.LocalRepoImpl
import com.lestec.pexels.data.getRoomClient
import com.lestec.pexels.domain.HttpRepo
import com.lestec.pexels.domain.LocalRepo
import com.lestec.pexels.domain.Repo
import com.lestec.pexels.ui.MainViewModel
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
                    single { HttpRepoImpl(KtorClientProvider.client, context) } bind HttpRepo::class
                    single { LocalRepoImpl(getRoomClient(context)) } bind LocalRepo::class
                    single { Repo(http = get(), local = get()) }
                    viewModel { MainViewModel(get()) }
                }
            )
        }
    }
}