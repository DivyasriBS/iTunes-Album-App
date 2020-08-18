package com.divyasri.itunes

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.divyasri.itunes.data.AppRepository
import com.divyasri.itunes.data.api.ApiService
import com.divyasri.itunes.data.models.ResultWrapper
import com.divyasri.itunes.data.models.SearchResponse
import com.divyasri.itunes.ui.search.SearchViewModel
import com.tw.gojek.data.preference.PreferenceHelper
import com.tw.gojek.ui.trending.LiveDataTestUtil
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchResultViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    private lateinit var viewModel: SearchViewModel
    @MockK
    private lateinit var appRepository: AppRepository
    @RelaxedMockK
    private lateinit var apiService: ApiService
    @MockK
    private lateinit var preferenceHelper: PreferenceHelper
    @MockK
    private lateinit var context: Context
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        appRepository = AppRepository(apiService)
        viewModel = SearchViewModel(appRepository, context)
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @Test
    fun `fetchSearchResults`() {
        runBlocking {
            launch(Dispatchers.Main) {

                val term = "all"

                val mockResponse = mockk<SearchResponse>(relaxed = true)
                val res = ResultWrapper.Success(mockResponse)
                every { apiService.getitunesSearch("all") } returns
                        CompletableDeferred(mockResponse)

                val resp = apiService.getitunesSearch(term)
                assertTrue(resp.isCompleted)

                val mutableLiveData =
                    MutableLiveData<ResultWrapper<SearchResponse>>()
                mutableLiveData.postValue(res)
                viewModel.repositoryLiveData = mutableLiveData
                viewModel.fetchSearchResults(term)

                assertEquals(
                    LiveDataTestUtil.getValue(viewModel.fetchSearchResults(term))
                    , mutableLiveData.value
                )

            }
        }
    }

    @Test
    fun `fetchGithubRepositoryError`() {
        runBlocking {
            launch(Dispatchers.Main) {

                val term = "all"

                val res = ResultWrapper.NetworkError(Exception(), "socket-timeout exception")

                val mockResponse = mockk<SearchResponse>(relaxed = true)
                every { apiService.getitunesSearch(term) } returns
                        CompletableDeferred(mockResponse)

                val resp = apiService.getitunesSearch(term)
                assertTrue(resp.isCompleted)

                val mutableLiveData =
                    MutableLiveData<ResultWrapper<SearchResponse>>()
                mutableLiveData.postValue(res)
                viewModel.repositoryLiveData = mutableLiveData
                viewModel.fetchSearchResults(term)

                assertEquals(
                    LiveDataTestUtil.getValue(viewModel.fetchSearchResults(term))
                    , mutableLiveData.value
                )

            }
        }
    }



    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

}