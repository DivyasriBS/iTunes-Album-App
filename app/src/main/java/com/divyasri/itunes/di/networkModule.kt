package com.divyasri.itunes.di

import com.divyasri.itunes.data.api.ApiService
import com.divyasri.itunes.utils.EndPoint.Companion.BASE_URL
import com.divyasri.itunes.utils.NetworkUtils
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val NETWORK_TIMEOUT = 10L

val networkModule = module {

    single {
        GsonBuilder().create()
    }

    single {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val cacheSize = (5 * 1024 * 1024).toLong()
        OkHttpClient.Builder().apply {

            cache(Cache(androidContext().cacheDir, cacheSize))
            connectTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
            retryOnConnectionFailure(true)
            //Start api logging only during debug state.
                addInterceptor(httpLoggingInterceptor)
            addInterceptor { chain ->
                // Get the request from the chain.
                var request = chain.request()

                /*
                *  Leveraging the advantage of using Kotlin,
                *  we initialize the request and change its header depending on whether
                *  the device is connected to Internet or not.
                */
                request = if (NetworkUtils.isNetworkAvailable(androidContext()))
                /*
                *  If there is Internet, get the cache that was stored 5 seconds ago.
                *  If the cache is older than 5 seconds, then discard it,
                *  and indicate an error in fetching the response.
                *  The 'max-age' attribute is responsible for this behavior.
                */
                    request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
                else
                /*
                *  If there is no Internet, get the cache that was stored 2 hours ago.
                *  If the cache is older than 2 hours, then discard it,
                *  and indicate an error in fetching the response.
                *  The 'max-stale' attribute is responsible for this behavior.
                *  The 'only-if-cached' attribute indicates to not retrieve new data; fetch the cache only instead.
                */
                    request.newBuilder().header(
                        "Cache-Control", "public, only-if-cached, max-stale="
                                + 60 * 60 * 2
                    ).build()

                chain.proceed(request)
            }
        }.build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(get())
            .build()
    }

    factory {
        get<Retrofit>().create(ApiService::class.java)
    }

}