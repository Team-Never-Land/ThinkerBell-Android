package com.neverland.domain.usecase.keyword

import com.neverland.domain.model.keyword.Keyword
import com.neverland.domain.repository.KeywordRepository
import javax.inject.Inject


class GetKeywordUseCase @Inject constructor(
    private val repository: KeywordRepository
){

    suspend operator fun invoke(ssaId: String): Result<List<Keyword>> {
        return repository.getKeyword(ssaId)
    }
}