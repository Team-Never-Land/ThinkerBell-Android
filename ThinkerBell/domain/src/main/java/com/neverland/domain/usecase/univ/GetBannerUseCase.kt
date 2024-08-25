package com.neverland.domain.usecase.univ

import com.neverland.domain.model.univ.Banner
import com.neverland.domain.repository.UnivRepository
import javax.inject.Inject


class GetBannerUseCase @Inject constructor(
    private val repository: UnivRepository
) {

    suspend operator fun invoke(): Result<List<Banner>> {
        return repository.getBanner()
    }
}