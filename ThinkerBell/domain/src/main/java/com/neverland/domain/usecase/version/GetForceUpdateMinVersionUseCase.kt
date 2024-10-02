package com.neverland.domain.usecase.version

import com.neverland.domain.repository.VersionRepository
import javax.inject.Inject

class GetForceUpdateMinVersionUseCase @Inject constructor(
    private val repository: VersionRepository
) {

    suspend operator fun invoke(): Result<String> {
        return repository.getForceUpdateMinVersion()
    }
}