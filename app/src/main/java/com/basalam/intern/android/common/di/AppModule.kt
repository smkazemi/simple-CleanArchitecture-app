package com.basalam.intern.android.common.di

import com.basalam.intern.android.common.data.repositories.AnimalFlowerRepositoryImpl
import com.basalam.intern.android.common.domain.repositories.AnimalFlowerRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun provideRepository(response: AnimalFlowerRepositoryImpl): AnimalFlowerRepository
}