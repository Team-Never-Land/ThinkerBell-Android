package com.neverland.domain.usecase.user

import com.neverland.domain.repository.UserRepository
import javax.inject.Inject

class PostUserInfoUseCase @Inject constructor(
    private val repository: UserRepository
){

    suspend operator fun invoke(ssaId: String, fcmToken: String): Result<Boolean> {
        return repository.postUserInfo(ssaId, fcmToken)
    }
}