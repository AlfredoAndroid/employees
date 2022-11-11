package com.innovation.mx.di

import android.app.Application
import android.content.Context
import com.innovation.mx.utils.AndroidSharedPreferences
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Reusable
    @Provides
    fun provideSharedPreferences(app: Application): AndroidSharedPreferences {
        return AndroidSharedPreferences(
            app.getSharedPreferences("SHARED_FILE", Context.MODE_PRIVATE)
        )
    }
}