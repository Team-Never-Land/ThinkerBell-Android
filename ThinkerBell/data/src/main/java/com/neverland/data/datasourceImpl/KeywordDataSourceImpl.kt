package com.neverland.data.datasourceImpl

import com.neverland.data.datasource.AlarmDataSource
import com.neverland.data.datasource.KeywordDataSource
import com.neverland.data.remote.model.BaseResponse
import com.neverland.data.remote.model.alarm.AlarmDTO
import com.neverland.data.remote.model.keyword.KeywordDTO
import com.neverland.data.remote.service.AlarmService
import com.neverland.data.remote.service.KeywordService
import retrofit2.Response
import javax.inject.Inject

class KeywordDataSourceImpl @Inject constructor(
    private val service: KeywordService
): KeywordDataSource {
    override suspend fun getKeyword(ssaId: String): Response<BaseResponse<List<KeywordDTO>>> {
        return service.getKeyword(ssaId)
    }

    override suspend fun postKeyword(
        keyword: String,
        ssaId: String
    ): Response<BaseResponse<String>> {
        return service.postKeyword(keyword = keyword, ssaId = ssaId)
    }

    override suspend fun deleteKeyword(
        keyword: String,
        ssaId: String
    ): Response<BaseResponse<String>> {
        return service.deleteKeyword(keyword = keyword, ssaId = ssaId)
    }
}