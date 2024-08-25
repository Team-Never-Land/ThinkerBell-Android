package com.neverland.data.di

import com.neverland.data.remote.service.AlarmService
import com.neverland.data.remote.service.BookmarkService
import com.neverland.data.remote.service.KeywordService
import com.neverland.data.remote.service.NoticeService
import com.neverland.data.remote.service.UnivService
import com.neverland.data.remote.service.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
internal class ServiceModule {

    @Provides
    fun providesAlarmService(client: Retrofit): AlarmService =
        client.create(AlarmService::class.java)

    @Provides
    fun providesBookmarkService(client: Retrofit): BookmarkService =
        client.create(BookmarkService::class.java)

    @Provides
    fun providesKeywordService(client: Retrofit): KeywordService =
        client.create(KeywordService::class.java)

    @Provides
    fun providesNoticeService(client: Retrofit): NoticeService =
        client.create(NoticeService::class.java)

    @Provides
    fun providesUnivService(client: Retrofit): UnivService =
        client.create(UnivService::class.java)

    @Provides
    fun providesUserService(client: Retrofit): UserService =
        client.create(UserService::class.java)
}