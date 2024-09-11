package com.neverland.data.repository

import com.neverland.data.datasource.KeywordDataSource
import com.neverland.data.utils.handleResponse
import com.neverland.domain.model.keyword.Keyword
import com.neverland.domain.repository.KeywordRepository
import javax.inject.Inject

class KeywordRepositoryImpl @Inject constructor(
    private val datasource: KeywordDataSource
) : KeywordRepository {

    override suspend fun getKeyword(ssaId: String): Result<List<Keyword>> {
        return handleResponse(
            call = { datasource.getKeyword(ssaId) },
            onSuccess = { data ->
                if (data!!.isNotEmpty()) {
                    data.map { Keyword(keyword = it.keyword) }
                } else {
                    emptyList()
                }
            }
        )
    }

    override suspend fun postKeyword(keyword: String, ssaId: String): Result<Boolean> {
        return handleResponse(
            dataNullSafe = true,
            call = { datasource.postKeyword(keyword, ssaId) },
            onSuccess = { true }
        )
    }

    override suspend fun deleteKeyword(keyword: String, ssaId: String): Result<Boolean> {
        return handleResponse(
            dataNullSafe = true,
            call = { datasource.deleteKeyword(keyword, ssaId) },
            onSuccess = { true }
        )
    }
}
