package com.neverland.data.repository

import com.neverland.data.datasource.NoticeDataSource
import com.neverland.domain.enums.NoticeType
import com.neverland.domain.model.PageableNotice
import com.neverland.domain.model.notice.AllNotices
import com.neverland.domain.model.notice.NoticeItem
import com.neverland.domain.model.notice.RecentNotices
import com.neverland.domain.repository.NoticeRepository
import javax.inject.Inject

class NoticeRepositoryImpl @Inject constructor(
    private val datasource: NoticeDataSource
) : NoticeRepository {

    override suspend fun getDormitoryNotices(
        page: Int,
        ssaId: String,
        campus: String
    ): Result<PageableNotice<NoticeItem.CommonNotice>> {
        return try {
            val res = datasource.getDormitoryNotices(page, ssaId, campus).body()

            if (res!!.code == 200) {
                if (res.data != null) {
                    Result.success(PageableNotice(
                        page = res.data.page,
                        size = res.data.size,
                        totalItems = res.data.totalItems,
                        items = res.data.items.map {
                            NoticeItem.CommonNotice(
                                id = it.id,
                                url = it.url,
                                campus = it.campus,
                                title = it.title,
                                pubDate = it.pubDate,
                                important = it.important ?: false,
                                marked = it.marked
                            )
                        }
                    ))
                } else {
                    Result.failure(Exception("Get DormitoryNotices failed: response is null data"))
                }
            } else {
                Result.failure(Exception("Get DormitoryNotices failed: ${res.data}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getDormitoryEntryNotices(
        page: Int,
        ssaId: String,
        campus: String
    ): Result<PageableNotice<NoticeItem.CommonNotice>> {
        return try {
            val res = datasource.getDormitoryEntryNotices(page, ssaId, campus).body()

            if (res!!.code == 200) {
                if (res.data != null) {
                    Result.success(PageableNotice(
                        page = res.data.page,
                        size = res.data.size,
                        totalItems = res.data.totalItems,
                        items = res.data.items.map {
                            NoticeItem.CommonNotice(
                                id = it.id,
                                url = it.url,
                                campus = it.campus,
                                title = it.title,
                                pubDate = it.pubDate,
                                important = it.important ?: false,
                                marked = it.marked
                            )
                        }
                    ))
                } else {
                    Result.failure(Exception("Get DormitoryNotices failed: response is null data"))
                }
            } else {
                Result.failure(Exception("Get DormitoryNotices failed: ${res.data}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getJobTrainingNotices(
        page: Int,
        ssaId: String
    ): Result<PageableNotice<NoticeItem.JobNotice>> {
        return try {
            val res = datasource.getJobTrainingNotices(page, ssaId).body()
            if (res!!.code == 200) {
                if (res.data != null) {
                    Result.success(PageableNotice(
                        page = res.data.page,
                        size = res.data.size,
                        totalItems = res.data.totalItems,
                        items = res.data.items.map {
                            NoticeItem.JobNotice(
                                id = it.id,
                                company = it.company,
                                year = it.year,
                                semester = it.semester,
                                period = it.period,
                                major = it.major,
                                recruitingNum = it.recrutingNum,
                                deadline = it.deadline,
                                jobName = it.jobName,
                                marked = it.marked
                            )
                        }
                    ))
                } else {
                    Result.failure(Exception("Get JobTrainingNotices failed: response is null data"))
                }
            } else {
                Result.failure(Exception("Get JobTrainingNotices failed: ${res.data}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getLibraryNotices(
        page: Int,
        ssaId: String,
        campus: String
    ): Result<PageableNotice<NoticeItem.CommonNotice>> {
        return try {
            val res = datasource.getLibraryNotices(page, ssaId, campus).body()
            if (res!!.code == 200) {
                if (res.data != null) {
                    Result.success(PageableNotice(
                        page = res.data.page,
                        size = res.data.size,
                        totalItems = res.data.totalItems,
                        items = res.data.items.map {
                            NoticeItem.CommonNotice(
                                id = it.id,
                                url = it.url,
                                campus = it.campus,
                                title = it.title,
                                pubDate = it.pubDate,
                                important = it.important ?: false,
                                marked = it.marked
                            )
                        }
                    ))
                } else {
                    Result.failure(Exception("Get LibraryNotices failed: response is null data"))
                }
            } else {
                Result.failure(Exception("Get LibraryNotices failed: ${res.data}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getTeachingNotices(
        page: Int,
        ssaId: String
    ): Result<PageableNotice<NoticeItem.CommonNotice>> {
        return try {
            val res = datasource.getTeachingNotices(page, ssaId).body()

            if (res!!.code == 200) {
                if (res.data != null) {
                    Result.success(PageableNotice(
                        page = res.data.page,
                        size = res.data.size,
                        totalItems = res.data.totalItems,
                        items = res.data.items.map {
                            NoticeItem.CommonNotice(
                                id = it.id,
                                url = it.url,
                                campus = it.campus,
                                title = it.title,
                                pubDate = it.pubDate,
                                important = it.important ?: false,
                                marked = it.marked
                            )
                        }
                    ))
                } else {
                    Result.failure(Exception("Get TeachingNotices failed: response is null data"))
                }
            } else {
                Result.failure(Exception("Get TeachingNotices failed: ${res.data}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAcademicNotices(
        page: Int, ssaId: String
    ): Result<PageableNotice<NoticeItem.CommonNotice>> {
        return try {
            val res = datasource.getAcademicNotices(page, ssaId).body()

            if (res!!.code == 200) {
                if (res.data != null) {
                    Result.success(
                        PageableNotice(
                            page = res.data.page,
                            size = res.data.size,
                            totalItems = res.data.totalItems,
                            items = res.data.items.map {
                                NoticeItem.CommonNotice(
                                    id = it.id,
                                    title = it.title,
                                    pubDate = it.pubDate,
                                    url = it.url,
                                    campus = it.campus,
                                    important = it.important ?: false,
                                    marked = it.marked
                                )
                            }
                        )
                    )
                } else {
                    Result.failure(Exception("Get AcademicNotices failed: response is null data"))
                }
            } else {
                Result.failure(Exception("Get AcademicNotices failed: ${res.data}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getEventNotices(
        page: Int,
        ssaId: String
    ): Result<PageableNotice<NoticeItem.CommonNotice>> {
        return try {
            val res = datasource.getEventNotices(page, ssaId).body()

            if (res!!.code == 200) {
                if (res.data != null) {
                    Result.success(
                        PageableNotice(
                            page = res.data.page,
                            size = res.data.size,
                            totalItems = res.data.totalItems,
                            items = res.data.items.map {
                                NoticeItem.CommonNotice(
                                    id = it.id,
                                    title = it.title,
                                    pubDate = it.pubDate,
                                    url = it.url,
                                    campus = it.campus,
                                    important = it.important ?: false,
                                    marked = it.marked
                                )
                            }
                        )
                    )
                } else {
                    Result.failure(Exception("Get EventNotices failed: response is null data"))
                }
            } else {
                Result.failure(Exception("Get EventNotices failed: ${res.data}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getNormalNotices(
        page: Int,
        ssaId: String
    ): Result<PageableNotice<NoticeItem.CommonNotice>> {
        return try {
            val res = datasource.getNormalNotices(page, ssaId).body()

            if (res!!.code == 200) {
                if (res.data != null) {
                    Result.success(
                        PageableNotice(
                            page = res.data.page,
                            size = res.data.size,
                            totalItems = res.data.totalItems,
                            items = res.data.items.map {
                                NoticeItem.CommonNotice(
                                    id = it.id,
                                    title = it.title,
                                    pubDate = it.pubDate,
                                    url = it.url,
                                    campus = it.campus,
                                    important = it.important ?: false,
                                    marked = it.marked
                                )
                            }
                        )
                    )
                } else {
                    Result.failure(Exception("Get NormalNotices failed: response is null data"))
                }
            } else {
                Result.failure(Exception("Get NormalNotices failed: ${res.data}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getRevisionNotices(
        page: Int,
        ssaId: String
    ): Result<PageableNotice<NoticeItem.CommonNotice>> {
        return try {
            val res = datasource.getRevisionNotices(page, ssaId).body()

            if (res!!.code == 200) {
                if (res.data != null) {
                    Result.success(
                        PageableNotice(
                            page = res.data.page,
                            size = res.data.size,
                            totalItems = res.data.totalItems,
                            items = res.data.items.map {
                                NoticeItem.CommonNotice(
                                    id = it.id,
                                    title = it.title,
                                    pubDate = it.pubDate,
                                    url = it.url,
                                    campus = it.campus,
                                    important = it.important ?: false,
                                    marked = it.marked
                                )
                            }
                        )
                    )
                } else {
                    Result.failure(Exception("Get RevisionNotices failed: response is null data"))
                }
            } else {
                Result.failure(Exception("Get RevisionNotices failed: ${res.data}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getSafetyNotices(
        page: Int,
        ssaId: String
    ): Result<PageableNotice<NoticeItem.CommonNotice>> {
        return try {
            val res = datasource.getSafetyNotices(page, ssaId).body()

            if (res!!.code == 200) {
                if (res.data != null) {
                    Result.success(
                        PageableNotice(
                            page = res.data.page,
                            size = res.data.size,
                            totalItems = res.data.totalItems,
                            items = res.data.items.map {
                                NoticeItem.CommonNotice(
                                    id = it.id,
                                    title = it.title,
                                    pubDate = it.pubDate,
                                    url = it.url,
                                    campus = it.campus,
                                    important = it.important ?: false,
                                    marked = it.marked
                                )
                            }
                        )
                    )
                } else {
                    Result.failure(Exception("Get SafetyNotices failed: response is null data"))
                }
            } else {
                Result.failure(Exception("Get SafetyNotices failed: ${res.data}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getScholarshipNotices(
        page: Int,
        ssaId: String
    ): Result<PageableNotice<NoticeItem.CommonNotice>> {
        return try {
            val res = datasource.getScholarship(page, ssaId).body()
            if (res!!.code == 200) {
                if (res.data != null) {


                    Result.success(
                        PageableNotice(
                            page = res.data.page,
                            size = res.data.size,
                            totalItems = res.data.totalItems,
                            items = res.data.items.map {
                                NoticeItem.CommonNotice(
                                    id = it.id,
                                    title = it.title,
                                    pubDate = it.pubDate,
                                    url = it.url,
                                    campus = it.campus,
                                    important = it.important ?: false,
                                    marked = it.marked
                                )
                            }
                        )
                    )
                } else {
                    Result.failure(Exception("Get ScholarshipNotices failed: response is null data"))
                }
            } else {

                Result.failure(Exception("Get ScholarshipNotices failed: ${res.data}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getStudentActsNotices(
        page: Int,
        ssaId: String
    ): Result<PageableNotice<NoticeItem.CommonNotice>> {
        return try {
            val res = datasource.getStudentActsNotices(page, ssaId).body()

            if (res!!.code == 200) {
                if (res.data != null) {
                    Result.success(
                        PageableNotice(
                            page = res.data.page,
                            size = res.data.size,
                            totalItems = res.data.totalItems,
                            items = res.data.items.map {
                                NoticeItem.CommonNotice(
                                    id = it.id,
                                    title = it.title,
                                    pubDate = it.pubDate,
                                    url = it.url,
                                    campus = it.campus,
                                    important = it.important ?: false,
                                    marked = it.marked
                                )
                            }
                        )
                    )
                } else {
                    Result.failure(Exception("Get StudentActsNotices failed: response is null data"))
                }
            } else {
                Result.failure(Exception("Get ScholarshipNotices failed: ${res.data}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCareerNotices(
        page: Int,
        ssaId: String
    ): Result<PageableNotice<NoticeItem.CommonNotice>> {
        return try {
            val res = datasource.getCareerNotices(page, ssaId).body()

            if (res!!.code == 200) {
                if (res.data != null) {
                    Result.success(
                        PageableNotice(
                            page = res.data.page,
                            size = res.data.size,
                            totalItems = res.data.totalItems,
                            items = res.data.items.map {
                                NoticeItem.CommonNotice(
                                    id = it.id,
                                    title = it.title,
                                    pubDate = it.pubDate,
                                    url = it.url,
                                    campus = it.campus,
                                    important = it.important ?: false,
                                    marked = it.marked
                                )
                            }
                        )
                    )
                } else {
                    Result.failure(Exception("Get CareerNotices failed: response is null data"))
                }
            } else {

                Result.failure(Exception("Get CareerNotices failed: ${res.data}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getBiddingNotices(
        page: Int,
        ssaId: String
    ): Result<PageableNotice<NoticeItem.CommonNotice>> {
        return try {
            val res = datasource.getBiddingNotices(page, ssaId).body()

            if (res!!.code == 200) {
                if (res.data != null) {
                    Result.success(
                        PageableNotice(
                            page = res.data.page,
                            size = res.data.size,
                            totalItems = res.data.totalItems,
                            items = res.data.items.map {
                                NoticeItem.CommonNotice(
                                    id = it.id,
                                    title = it.title,
                                    pubDate = it.pubDate,
                                    url = it.url,
                                    campus = it.campus,
                                    important = it.important ?: false,
                                    marked = it.marked
                                )
                            }
                        )
                    )
                } else {
                    Result.failure(Exception("Get BiddingNotices failed: response is null data"))
                }
            } else {

                Result.failure(Exception("Get BiddingNotices failed: ${res.data}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun searchNoticesByCategory(
        noticeType: NoticeType,
        keyword: String,
        ssaId: String
    ): Result<List<NoticeItem>> {
        return try {
            val res = datasource.searchNotices(keyword = keyword, ssaId = ssaId).body()

            if (res!!.code == 200) {
                val data = res.data
                val noticeItems: List<NoticeItem>

                if (data != null) {
                    if (noticeType != NoticeType.JOB_TRAINING_NOTICE) {
                        val notices = when (noticeType) {
                            NoticeType.NORMAL_NOTICE -> data.normalNotices
                            NoticeType.EVENT_NOTICE -> data.eventNotices
                            NoticeType.ACADEMIC_NOTICE -> data.academicNotices
                            NoticeType.SCHOLARSHIP_NOTICE -> data.scholarshipNotices
                            NoticeType.CAREER_NOTICE -> data.careerNotices
                            NoticeType.STUDENT_ACTS_NOTICE -> data.studentActsNotices
                            NoticeType.BIDDING_NOTICE -> data.biddingNotices
                            NoticeType.SAFETY_NOTICE -> data.safetyNotices
                            NoticeType.REVISION_NOTICE -> data.revisionNotices
                            NoticeType.DORMITORY_NOTICE -> data.dormitoryNotices
                            NoticeType.DORMITORY_ENTRY_NOTICE -> data.dormitoryEntryNotices
                            NoticeType.LIBRARY_NOTICE -> data.libraryNotices
                            NoticeType.TEACHING_NOTICE -> data.teachingNotices
                            else -> null
                        }

                        noticeItems = (notices?.map {
                            NoticeItem.CommonNotice(
                                id = it.id,
                                title = it.title,
                                pubDate = it.pubDate,
                                url = it.url,
                                campus = it.campus,
                                marked = it.marked
                            )
                        } ?: emptyList())
                    } else {
                        noticeItems = (data.jobTrainingNotices?.map {
                            NoticeItem.JobNotice(
                                id = it.id,
                                company = it.company,
                                year = it.year,
                                semester = it.semester,
                                deadline = it.deadline,
                                jobName = it.jobName,
                                major = it.major,
                                period = it.period,
                                recruitingNum = it.recrutingNum
                            )
                        } ?: emptyList())
                    }

                    Result.success(noticeItems)
                } else {
                    Result.failure(Exception("Failed to Search notices: response data is null"))
                }
            } else {
                Result.failure(Exception("Failed to Search notices: ${res.data}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getRecentNotices(ssaId: String): Result<RecentNotices> {
        return try {
            val res = datasource.getRecentNotices(ssaId).body()

            if (res!!.code == 200) {
                if (res.data != null) {
                    val normalNotices = res.data.normalNotices?.map {
                        NoticeItem.CommonNotice(
                            id = it.id,
                            pubDate = it.pubDate,
                            url = it.url,
                            title = it.title
                        )
                    } ?: emptyList()
                    val academicNotices = res.data.academicNotices?.map {
                        NoticeItem.CommonNotice(
                            id = it.id,
                            pubDate = it.pubDate,
                            url = it.url,
                            title = it.title
                        )
                    } ?: emptyList()
                    val scholarshipNotices = res.data.scholarshipNotices?.map {
                        NoticeItem.CommonNotice(
                            id = it.id,
                            pubDate = it.pubDate,
                            url = it.url,
                            title = it.title
                        )
                    } ?: emptyList()
                    val careerNotices = res.data.careerNotices?.map {
                        NoticeItem.CommonNotice(
                            id = it.id,
                            pubDate = it.pubDate,
                            url = it.url,
                            title = it.title
                        )
                    } ?: emptyList()
                    val eventNotices = res.data.eventNotices?.map {
                        NoticeItem.CommonNotice(
                            id = it.id,
                            pubDate = it.pubDate,
                            url = it.url,
                            title = it.title
                        )
                    } ?: emptyList()

                    Result.success(
                        RecentNotices(
                            academicNotices = academicNotices,
                            careerNotices = careerNotices,
                            scholarshipNotices = scholarshipNotices,
                            eventNotices = eventNotices,
                            normalNotices = normalNotices
                        )
                    )
                } else {
                    Result.failure(Exception("Get Recent failed: response is null data"))
                }
            } else {

                Result.failure(Exception("Get Recent failed: ${res.data}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun searchAllNoticesByCategory(
        keyword: String,
        ssaId: String
    ): Result<AllNotices> {
        return try {
            val res = datasource.searchNotices(keyword = keyword, ssaId = ssaId).body()
            if (res!!.code == 200) {
                if (res.data != null) {
                    val bookmarkNotice = AllNotices(
                        safetyNotice = res.data.safetyNotices?.map {
                            NoticeItem.CommonNotice(
                                id = it.id,
                                title = it.title,
                                pubDate = it.pubDate,
                                url = it.url,
                                marked = it.marked
                            )
                        },
                        revisionNotice = res.data.revisionNotices?.map {
                            NoticeItem.CommonNotice(
                                id = it.id,
                                title = it.title,
                                pubDate = it.pubDate,
                                url = it.url,
                                marked = it.marked
                            )
                        },
                        libraryNotice = res.data.libraryNotices?.map {
                            NoticeItem.CommonNotice(
                                id = it.id,
                                title = it.title,
                                pubDate = it.pubDate,
                                url = it.url,
                                marked = it.marked,
                                important = it.important ?: false,
                                campus = it.campus
                            )
                        },
                        dormitoryNotice = res.data.dormitoryNotices?.map {
                            NoticeItem.CommonNotice(
                                id = it.id,
                                title = it.title,
                                pubDate = it.pubDate,
                                url = it.url,
                                marked = it.marked,
                                important = it.important ?: false,
                                campus = it.campus
                            )
                        },
                        teachingNotice = res.data.teachingNotices?.map {
                            NoticeItem.CommonNotice(
                                id = it.id,
                                title = it.title,
                                pubDate = it.pubDate,
                                url = it.url,
                                marked = it.marked,
                                important = it.important ?: false
                            )
                        },
                        jobTrainingNotice = res.data.jobTrainingNotices?.map {
                            NoticeItem.JobNotice(
                                id = it.id,
                                company = it.company,
                                year = it.year,
                                semester = it.semester,
                                recruitingNum = it.recrutingNum,
                                major = it.major,
                                deadline = it.deadline,
                                period = it.period,
                                jobName = it.jobName,
                                marked = it.marked
                            )
                        },
                        eventNotice = res.data.eventNotices?.map {
                            NoticeItem.CommonNotice(
                                id = it.id,
                                title = it.title,
                                pubDate = it.pubDate,
                                url = it.url,
                                marked = it.marked
                            )
                        },
                        studentActsNotice = res.data.studentActsNotices?.map {
                            NoticeItem.CommonNotice(
                                id = it.id,
                                title = it.title,
                                pubDate = it.pubDate,
                                url = it.url,
                                marked = it.marked
                            )
                        },
                        academicNotice = res.data.academicNotices?.map {
                            NoticeItem.CommonNotice(
                                id = it.id,
                                title = it.title,
                                pubDate = it.pubDate,
                                url = it.url,
                                marked = it.marked,
                                important = it.important ?: false
                            )
                        },
                        careerNotice = res.data.careerNotices?.map {
                            NoticeItem.CommonNotice(
                                id = it.id,
                                title = it.title,
                                pubDate = it.pubDate,
                                url = it.url,
                                marked = it.marked
                            )
                        },
                        biddingNotice = res.data.biddingNotices?.map {
                            NoticeItem.CommonNotice(
                                id = it.id,
                                title = it.title,
                                pubDate = it.pubDate,
                                url = it.url,
                                marked = it.marked
                            )
                        },
                        dormitoryEntryNotice = res.data.dormitoryEntryNotices?.map {
                            NoticeItem.CommonNotice(
                                id = it.id,
                                title = it.title,
                                pubDate = it.pubDate,
                                url = it.url,
                                marked = it.marked,
                                important = it.important ?: false,
                                campus = it.campus
                            )
                        },
                        scholarshipNotice = res.data.scholarshipNotices?.map {
                            NoticeItem.CommonNotice(
                                id = it.id,
                                title = it.title,
                                pubDate = it.pubDate,
                                url = it.url,
                                marked = it.marked
                            )
                        },
                        normalNotice = res.data.normalNotices?.map {
                            NoticeItem.CommonNotice(
                                id = it.id,
                                title = it.title,
                                pubDate = it.pubDate,
                                url = it.url,
                                marked = it.marked
                            )
                        }
                    )
                    Result.success(bookmarkNotice)
                } else {
                    Result.failure(Exception("Search failed: response is null data"))
                }
            } else {

                Result.failure(Exception("Get BookmarkNotices failed: ${res.data}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}