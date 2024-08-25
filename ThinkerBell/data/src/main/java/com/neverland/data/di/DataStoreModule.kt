package com.neverland.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.neverland.data.datasource.DataStoreLocaleDataSource
import com.neverland.data.datasourceImpl.DataStoreLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataStoreModule {

    @Binds
    @Singleton
    abstract fun bindsDataStoreLocalDataSource(impl: DataStoreLocalDataSourceImpl): DataStoreLocaleDataSource

    companion object {
        @Provides
        @Singleton
        fun providesUserStore(
            @ApplicationContext context: Context
        ): DataStore<Preferences> {
            return PreferenceDataStoreFactory.create(
                produceFile = {
                    context.preferencesDataStoreFile("thinkerbell_preference")
                }
            )
        }
    }
}