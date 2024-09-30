package com.neverland.data.repository

import com.neverland.data.datasource.UnivDataSource
import com.neverland.data.utils.handleResponse
import com.neverland.domain.model.univ.AcademicSchedule
import com.neverland.domain.model.univ.Banner
import com.neverland.domain.model.univ.DeptContact
import com.neverland.domain.model.univ.DeptUrl
import com.neverland.domain.repository.UnivRepository
import javax.inject.Inject

class UnivRepositoryImpl @Inject constructor(
    private val datasource: UnivDataSource
): UnivRepository {

    override suspend fun getDeptUrl(): Result<List<DeptUrl>> {
        return handleResponse(
            dataNullSafe = false,
            call = { datasource.getDeptUrl() },
            onSuccess = { data ->
                data?.map { DeptUrl(college = it.college, school = it.school, url = it.url) } ?: emptyList()
            }
        )
    }

    override suspend fun getDeptContact(): Result<List<DeptContact>> {
        return handleResponse(
            dataNullSafe = false,
            call = { datasource.getDeptContact() },
            onSuccess = { data ->
                data?.map { DeptContact(college = it.college, campus = it.campus, contact = it.contact, major = it.major) } ?: emptyList()
            }
        )
    }

    override suspend fun getMonthlyAcademicSchedule(year: Int, month: Int, ssaId: String): Result<List<AcademicSchedule>> {
        return handleResponse(
            dataNullSafe = false,
            call = { datasource.getMonthlyAcademicSchedule(year, month, ssaId) },
            onSuccess = { data ->
                data?.map { AcademicSchedule(id = it.id, title = it.title, marked = it.marked, startDate = it.startDate, endDate = it.endDate) } ?: emptyList()
            }
        )
    }

    override suspend fun getBanner(): Result<List<Banner>> {
        return handleResponse(
            dataNullSafe = false,
            call = { datasource.getBanner() },
            onSuccess = { data ->
                    data?.map { Banner(id = it.id, title = it.title, s3Url = it.s3Url, noticeUrl = it.noticeUrl) } ?: emptyList()
            }
        )
    }
}
