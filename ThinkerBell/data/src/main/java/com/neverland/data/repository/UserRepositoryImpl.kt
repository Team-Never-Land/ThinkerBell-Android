package com.neverland.data.repository

import com.neverland.data.datasource.UserDataSource
import com.neverland.data.remote.model.user.PostUserInfoReqDTO
import com.neverland.data.utils.handleResponse
import com.neverland.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val datasource: UserDataSource
): UserRepository {

    override suspend fun postUserInfo(ssaId: String, fcmToken: String): Result<Boolean> {
        return handleResponse(
            dataNullSafe = true,
            call = { datasource.postUserInfo(PostUserInfoReqDTO(ssaId = ssaId, deviceToken = fcmToken)) },
            onSuccess = { true }
        )
    }

}