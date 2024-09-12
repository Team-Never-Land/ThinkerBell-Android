package com.neverland.data.di

import com.neverland.data.repository.AlarmRepositoryImpl
import com.neverland.data.repository.BookmarkRepositoryImpl
import com.neverland.data.repository.KeywordRepositoryImpl
import com.neverland.data.repository.NoticeRepositoryImpl
import com.neverland.data.repository.ReportRepositoryImpl
import com.neverland.data.repository.UnivRepositoryImpl
import com.neverland.data.repository.UserRepositoryImpl
import com.neverland.domain.repository.AlarmRepository
import com.neverland.domain.repository.BookmarkRepository
import com.neverland.domain.repository.KeywordRepository
import com.neverland.domain.repository.NoticeRepository
import com.neverland.domain.repository.ReportRepository
import com.neverland.domain.repository.UnivRepository
import com.neverland.domain.repository.UserRepository
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
    abstract fun bindsAlarmRepository(impl: AlarmRepositoryImpl): AlarmRepository

    @Binds
    @Singleton
    abstract fun bindsBookmarkRepository(impl: BookmarkRepositoryImpl): BookmarkRepository

    @Binds
    @Singleton
    abstract fun bindsKeywordRepository(impl: KeywordRepositoryImpl): KeywordRepository

    @Binds
    @Singleton
    abstract fun bindsNoticeRepository(impl: NoticeRepositoryImpl): NoticeRepository

    @Binds
    @Singleton
    abstract fun bindsUnivRepository(impl: UnivRepositoryImpl): UnivRepository

    @Binds
    @Singleton
    abstract fun bindsUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun bindsReportRepository(impl: ReportRepositoryImpl): ReportRepository
}