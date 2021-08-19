package com.shortly.di

import android.os.Build
import com.shortly.model.common.BuildSdkVersionChecker
import com.shortly.model.repository.HistoryRepository
import com.shortly.model.repository.HistoryRepositoryImpl
import com.shortly.model.repository.ShortenRepository
import com.shortly.model.repository.ShortenRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun bindShortenRepository(shortenRepositoryImpl: ShortenRepositoryImpl): ShortenRepository

    @Binds
    abstract fun bindHistoryRepository(historyRepositoryImpl: HistoryRepositoryImpl): HistoryRepository

    companion object {
        @Provides
        @Named("IO_DISPATCHER")
        fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

        @Provides
        @Named("DEFAULT_DISPATCHER")
        fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

        @Provides
        @Singleton
        fun provideBuildSdkVersion(): BuildSdkVersionChecker {
            return BuildSdkVersionChecker(Build.VERSION.SDK_INT)
        }
    }
}