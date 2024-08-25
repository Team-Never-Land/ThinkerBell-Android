package com.neverland.data.di

import com.neverland.data.datasource.AlarmDataSource
import com.neverland.data.datasource.BookmarkDataSource
import com.neverland.data.datasource.KeywordDataSource
import com.neverland.data.datasource.NoticeDataSource
import com.neverland.data.datasource.UnivDataSource
import com.neverland.data.datasource.UserDataSource
import com.neverland.data.datasourceImpl.AlarmDataSourceImpl
import com.neverland.data.datasourceImpl.BookmarkDataSourceImpl
import com.neverland.data.datasourceImpl.KeywordDataSourceImpl
import com.neverland.data.datasourceImpl.NoticeDataSourceImpl
import com.neverland.data.datasourceImpl.UnivDataSourceImpl
import com.neverland.data.datasourceImpl.UserDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindsAlarmDataSource(impl: AlarmDataSourceImpl): AlarmDataSource

    @Binds
    @Singleton
    abstract fun bindsBookmarkDataSource(impl: BookmarkDataSourceImpl): BookmarkDataSource

    @Binds
    @Singleton
    abstract fun bindsKeywordDataSource(impl: KeywordDataSourceImpl): KeywordDataSource

    @Binds
    @Singleton
    abstract fun bindsNoticeDataSource(impl: NoticeDataSourceImpl): NoticeDataSource

    @Binds
    @Singleton
    abstract fun bindsUnivDataSource(impl: UnivDataSourceImpl): UnivDataSource

    @Binds
    @Singleton
    abstract fun bindsUserDataSource(impl: UserDataSourceImpl): UserDataSource
}