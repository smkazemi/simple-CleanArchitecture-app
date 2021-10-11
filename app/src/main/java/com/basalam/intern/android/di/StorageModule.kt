package com.basalam.intern.android.di

import android.content.Context
import androidx.room.Room
import com.basalam.intern.android.data.local.database.AppLocalDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


/**
 * By the grace of Allah, Created by Sayed MohammadReza Kazemi
 * on 5/18/2021.
 */

@InstallIn(SingletonComponent::class)
@Module
class StorageModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppLocalDataBase {

        return Room.databaseBuilder(context, AppLocalDataBase::class.java, "basalam-test.db").build()
    }
}