package com.neverland.domain.repository

import com.neverland.domain.model.univ.AcademicSchedule
import com.neverland.domain.model.univ.Banner
import com.neverland.domain.model.univ.DeptContact
import com.neverland.domain.model.univ.DeptUrl

interface UnivRepository {

    suspend fun getDeptUrl(): Result<List<DeptUrl>>

    suspend fun getDeptContact(): Result<List<DeptContact>>

    suspend fun getMonthlyAcademicSchedule(year: Int, month: Int, ssaId: String): Result<List<AcademicSchedule>>

    suspend fun getBanner(): Result<List<Banner>>
}