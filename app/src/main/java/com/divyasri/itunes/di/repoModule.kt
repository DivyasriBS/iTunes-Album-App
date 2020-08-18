package com.divyasri.itunes.di

import com.divyasri.itunes.data.AppRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory { AppRepository(get()) }
}