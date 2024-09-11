package com.neverland.data.repository

import com.neverland.data.datasource.UnivDataSource
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
        return try {
            val res = datasource.getDeptUrl().body()

            if(res!!.code == 200){
                if(res.data != null){
                    Result.success(res.data.map { DeptUrl(college = it.college, school = it.school, url = it.url) })
                } else {
                    Result.failure(Exception("Get department url failed: response is null data"))
                }
            } else {
                Result.failure(Exception("Get department url failed: ${res.data}"))
            }
        } catch (e : Exception){
            Result.failure(e)
        }
    }

    override suspend fun getDeptContact(): Result<List<DeptContact>> {
        return try {
            val res = datasource.getDeptContact().body()
            if(res!!.code == 200){
                if(res.data != null){
                    Result.success(res.data.map { DeptContact(college = it.college, campus = it.campus, contact = it.contact, major = it.major) })
                } else {
                    Result.failure(Exception("Get department contact failed: response is null data"))
                }
            } else {
                Result.failure(Exception("Get department contact failed: ${res.data}"))
            }
        } catch (e : Exception){
            Result.failure(e)
        }
    }

    override suspend fun getMonthlyAcademicSchedule(month: Int, ssaId: String): Result<List<AcademicSchedule>> {
        return try {
            val res = datasource.getMonthlyAcademicSchedule(month, ssaId).body()
            if(res!!.code == 200){
                if(res.data != null){
                    Result.success(res.data.map { AcademicSchedule(id = it.id, title = it.title, marked = it.marked, startDate = it.startDate, endDate = it.endDate) })
                } else {
                    Result.failure(Exception("Get monthly academic schedule failed: response is null data"))
                }
            } else {
                Result.failure(Exception("Get monthly academic schedule failed: ${res.data}"))
            }
        } catch (e : Exception){
            Result.failure(e)
        }
    }

    override suspend fun getBanner(): Result<List<Banner>> {
        return try {
            val res = datasource.getBanner().body()
            if(res!!.code == 200){
                if(res.data != null){
                    Result.success(res.data.map { Banner(id = it.id, title = it.title, s3Url = it.s3Url, noticeUrl = it.noticeUrl) })
                } else {
                    Result.failure(Exception("Get banner failed: response is null data"))
                }
            } else {
                Result.failure(Exception("Get banner failed: ${res.data}"))
            }
        } catch (e : Exception){
            Result.failure(e)
        }
    }
}