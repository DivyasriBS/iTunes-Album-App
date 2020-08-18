package com.divyasri.itunes.ui.base

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel

abstract class BaseViewModel   : ViewModel() {

    var hideProgress = ObservableBoolean()

    fun setIsLoading(isLoading: Boolean) {
        hideProgress.set(isLoading)
    }

}