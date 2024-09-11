package com.neverland.data.repository

import com.neverland.data.datasource.UserDataSource
import com.neverland.data.remote.model.user.PostUserInfoReqDTO
import com.neverland.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val datasource: UserDataSource
): UserRepository {

    override suspend fun postUserInfo(ssaId: String, fcmToken: String): Result<Boolean> {
        return try {
            val res = datasource.postUserInfo(PostUserInfoReqDTO(ssaId = ssaId, deviceToken = fcmToken)).body()

            if(res!!.code == 200){
                Result.success(true)
            } else {
                Result.failure(Exception("Post User Info failed: ${res.data}"))
            }
        } catch (e : Exception){
            Result.failure(e)
        }
    }
}