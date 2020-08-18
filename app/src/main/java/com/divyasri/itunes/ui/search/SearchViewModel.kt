package com.divyasri.itunes.ui.search

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.divyasri.itunes.data.AppRepository
import com.divyasri.itunes.data.models.ResultWrapper
import com.divyasri.itunes.data.models.SearchResponse
import com.divyasri.itunes.ui.base.BaseViewModel
import com.tw.gojek.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel ( private val repository: AppRepository, private val context: Context
) : BaseViewModel() {
    private val toastMessage = SingleLiveEvent<String>()
    var repositoryLiveData =
        MutableLiveData<ResultWrapper<SearchResponse>>()

    fun fetchSearchResults(searchText: String): MutableLiveData<ResultWrapper<SearchResponse>> {
        /* if (!NetworkUtils.isOnline(context)) {
             toastMessage.value = context.getString(R.string.no_internet_message)
         } else {
        setIsLoading(true)*/
        viewModelScope.launch(Dispatchers.Main) {
            val response = repository.getItunesSearch(
                searchText
            )
            // setIsLoading(false)
            repositoryLiveData.value = response
        }
        //}
        return repositoryLiveData
    }
}

