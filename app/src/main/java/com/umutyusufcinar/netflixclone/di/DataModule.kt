package com.umutyusufcinar.netflixclone.di

import com.umutyusufcinar.netflixclone.repository.HomeDataSource
import com.umutyusufcinar.netflixclone.repository.HomeDataSourceImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class DataModule {
    @Singleton
    @Binds
    abstract fun provideHomeDataSource(dataSource: HomeDataSourceImpl): HomeDataSource
}