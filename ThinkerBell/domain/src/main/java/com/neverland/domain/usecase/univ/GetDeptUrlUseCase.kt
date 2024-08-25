package com.neverland.domain.usecase.univ

import com.neverland.domain.model.univ.DeptUrl
import com.neverland.domain.repository.UnivRepository
import javax.inject.Inject

class GetDeptUrlUseCase @Inject constructor(
    private val repository: UnivRepository
) {

    suspend operator fun invoke(): Result<Map<String, List<DeptUrl>>> {
        val res = repository.getDeptUrl()

        return if(res.isSuccess){
            Result.success(res.getOrNull().orEmpty().groupBy { deptUrl -> deptUrl.school })
        } else {
            Result.failure(res.exceptionOrNull() ?: Exception())
        }
    }
}