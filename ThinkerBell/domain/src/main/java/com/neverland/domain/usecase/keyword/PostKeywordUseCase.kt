package com.neverland.domain.usecase.keyword

import com.neverland.domain.repository.KeywordRepository
import javax.inject.Inject

class PostKeywordUseCase @Inject constructor(
    private val repository: KeywordRepository
) {

    suspend operator fun invoke(ssaId: String, keyword: String): Result<Boolean> {
        return repository.postKeyword(ssaId = ssaId, keyword = keyword)
    }
}