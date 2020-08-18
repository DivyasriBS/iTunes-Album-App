package com.divyasri.itunes

import android.app.Application
import com.divyasri.itunes.di.networkModule
import com.divyasri.itunes.di.repositoryModule
import com.divyasri.itunes.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

private var instance: SearchApplication? = null


open class SearchApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this

        //Koin DI initialization.
        startKoin {
            printLogger() // Koin Logger
            androidContext(this@SearchApplication)
            modules(listOf(networkModule, viewModelModule, repositoryModule))
        }

    }

    companion object {
        fun getInstance(): SearchApplication? {
            return instance
        }
    }
}