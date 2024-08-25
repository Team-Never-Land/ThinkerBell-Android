package com.neverland.domain.usecase.univ

import com.neverland.domain.model.univ.DeptContact
import com.neverland.domain.repository.UnivRepository
import javax.inject.Inject

class GetDeptContactUseCase @Inject constructor(
    private val repository: UnivRepository
) {

    suspend operator fun invoke(): Result<Map<String, Map<String, List<DeptContact>>>> {
        val res = repository.getDeptContact()

        return if (res.isSuccess) {
            val deptContacts = res.getOrNull().orEmpty()

            val groupedByCampus = deptContacts.groupBy { it.campus }
            val groupedByCampusAndCollege = groupedByCampus.mapValues { (_, contacts) ->
                contacts.groupBy { it.college }
            }

            Result.success(groupedByCampusAndCollege)
        } else {
            Result.failure(res.exceptionOrNull() ?: Exception("Unknown error occurred"))
        }
    }
}