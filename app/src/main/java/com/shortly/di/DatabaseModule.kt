package com.shortly.di

import android.content.Context
import androidx.room.Room
import com.shortly.model.database.AppDatabase
import com.shortly.model.database.HistoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java, "AppDatabase"
        ).build()
    }

    @Provides
    fun provideMovieDao(appDatabase: AppDatabase): HistoryDao {
        return appDatabase.historyDao()
    }
}