package com.example.mviredux.di

import com.example.mviredux.redux.ApplicationState
import com.example.mviredux.redux.Store
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationStoreModule {

    @Provides
    @Singleton
    fun providesApplicationStateStore():Store<ApplicationState> {
        return Store(ApplicationState())
    }
}