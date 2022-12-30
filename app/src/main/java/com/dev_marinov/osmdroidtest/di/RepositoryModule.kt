package com.dev_marinov.osmdroidtest.di

import com.dev_marinov.osmdroidtest.data.Repository
import com.dev_marinov.osmdroidtest.domain.IRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRepository(repository: Repository): IRepository
}