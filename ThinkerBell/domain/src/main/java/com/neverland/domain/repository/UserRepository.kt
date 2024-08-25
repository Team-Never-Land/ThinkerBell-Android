package com.neverland.domain.repository


interface UserRepository {

    suspend fun postUserInfo(ssaId: String, fcmToken: String): Result<Boolean>
}