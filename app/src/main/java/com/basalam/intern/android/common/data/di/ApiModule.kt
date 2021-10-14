package com.basalam.intern.android.common.data.di

import com.basalam.intern.android.common.data.remote.BasalamService
import com.basalam.intern.android.common.util.Constant
import com.basalam.intern.android.common.util.toLog
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * By the grace of Allah, Created by Sayed MohammadReza Kazemi
 * on 5/18/2021.
 */

@InstallIn(SingletonComponent::class)
@Module
class ApiModule {

    @Provides
    fun provideApiService(): BasalamService {

        val logger = HttpLoggingInterceptor {
            it.toLog("okhttp")
        }.apply { level = HttpLoggingInterceptor.Level.BASIC }

        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .readTimeout(100, TimeUnit.SECONDS)
            .connectTimeout(100, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder().apply {
            baseUrl(Constant.baseURL)
            client(client)
            addConverterFactory(GsonConverterFactory.create())

        }.build().create(BasalamService::class.java)

    }

}