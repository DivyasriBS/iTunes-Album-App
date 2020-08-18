package com.divyasri.itunes.di

import com.divyasri.itunes.ui.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
 viewModel {
        SearchViewModel(get(),get())
    }
}
