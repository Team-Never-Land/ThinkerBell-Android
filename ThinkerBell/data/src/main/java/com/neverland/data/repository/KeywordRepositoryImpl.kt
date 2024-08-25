package com.neverland.data.repository

import com.neverland.data.datasource.KeywordDataSource
import com.neverland.domain.model.keyword.Keyword
import com.neverland.domain.repository.KeywordRepository
import org.json.JSONObject
import javax.inject.Inject

class KeywordRepositoryImpl @Inject constructor(
    private val datasource: KeywordDataSource
): KeywordRepository {

    override suspend fun getKeyword(ssaId: String): Result<List<Keyword>> {
        return try {
            val res = datasource.getKeyword(ssaId).body()

            if(res!!.code == 200){
                if(res.data != null) {
                    if(res.data.isNotEmpty()) {
                        Result.success(res.data.map { Keyword(keyword = it.keyword) })
                    } else {
                        Result.success(emptyList())
                    }
                } else {
                    Result.failure(Exception("Get Keyword failed: response is null data"))
                }
            } else {
                Result.failure(Exception("Get Keyword failed: ${res.message}"))
            }
        } catch (e : Exception) {
            Result.failure(e)
        }
    }

    override suspend fun postKeyword(keyword: String, ssaId: String): Result<Boolean> {
        return try {
            val res = datasource.postKeyword(keyword, ssaId).body()
            if(res!!.code == 200){
                Result.success(true)
            } else {
                Result.failure(Exception("Post Keyword failed: ${res.message}"))
            }
        } catch (e : Exception){
            Result.failure(e)
        }
    }

    override suspend fun deleteKeyword(keyword: String, ssaId: String): Result<Boolean> {
        return try {
            val res = datasource.deleteKeyword(keyword, ssaId).body()

            if(res!!.code == 200){
                Result.success(true)
            } else {
                Result.failure(Exception("Delete Keyword failed: ${res.message}"))
            }
        } catch (e : Exception){
            Result.failure(e)
        }
    }
}