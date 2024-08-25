package com.neverland.data.datasourceImpl

import com.neverland.data.datasource.UserDataSource
import com.neverland.data.remote.model.BaseResponse
import com.neverland.data.remote.model.user.PostUserInfoReqDTO
import com.neverland.data.remote.service.UserService
import retrofit2.Response
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val service: UserService
): UserDataSource {

    override suspend fun postUserInfo(body: PostUserInfoReqDTO): Response<BaseResponse<String>> {
        return service.postUserInfo(body)
    }
}