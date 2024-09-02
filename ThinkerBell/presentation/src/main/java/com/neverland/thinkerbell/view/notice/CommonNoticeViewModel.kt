package com.neverland.thinkerbell.view.notice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neverland.core.utils.LoggerUtil
import com.neverland.domain.enums.NoticeType
import com.neverland.domain.model.notice.NoticeItem
import com.neverland.domain.usecase.bookmark.DeleteBookmarkUseCase
import com.neverland.domain.usecase.bookmark.PostBookmarkUseCase
import com.neverland.domain.usecase.notice.GetAcademicNoticesUseCase
import com.neverland.domain.usecase.notice.GetBiddingNoticesUseCase
import com.neverland.domain.usecase.notice.GetCareerNoticesUseCase
import com.neverland.domain.usecase.notice.GetDormitoryEntryNoticesUseCase
import com.neverland.domain.usecase.notice.GetDormitoryNoticesUseCase
import com.neverland.domain.usecase.notice.GetEventNoticesUseCase
import com.neverland.domain.usecase.notice.GetJobTrainingNoticesUseCase
import com.neverland.domain.usecase.notice.GetLibraryNoticesUseCase
import com.neverland.domain.usecase.notice.GetNormalNoticesUseCase
import com.neverland.domain.usecase.notice.GetRevisionNoticesUseCase
import com.neverland.domain.usecase.notice.GetSafetyNoticesUseCase
import com.neverland.domain.usecase.notice.GetScholarshipNoticesUseCase
import com.neverland.domain.usecase.notice.GetStudentActsNoticesUseCase
import com.neverland.domain.usecase.notice.GetTeachingNoticesUseCase
import com.neverland.domain.usecase.notice.SearchNoticesByCategoryUseCase
import com.neverland.thinkerbell.base.ThinkerBellApplication.Companion.application
import com.neverland.thinkerbell.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.ceil

@HiltViewModel
class CommonNoticeViewModel @Inject constructor(
    private val postBookmarkUseCase: PostBookmarkUseCase,
    private val deleteBookmarkUseCase: DeleteBookmarkUseCase,
    private val searchNoticesByCategoryUseCase: SearchNoticesByCategoryUseCase,
    private val getAcademicNoticesUseCase: GetAcademicNoticesUseCase,
    private val getBiddingNoticesUseCase: GetBiddingNoticesUseCase,
    private val getCareerNoticesUseCase: GetCareerNoticesUseCase,
    private val getDormitoryNoticesUseCase: GetDormitoryNoticesUseCase,
    private val getDormitoryEntryNoticesUseCase: GetDormitoryEntryNoticesUseCase,
    private val getEventNoticesUseCase: GetEventNoticesUseCase,
    private val getJobTrainingNoticesUseCase: GetJobTrainingNoticesUseCase,
    private val getLibraryNoticesUseCase: GetLibraryNoticesUseCase,
    private val getNormalNoticesUseCase: GetNormalNoticesUseCase,
    private val getRevisionNoticesUseCase: GetRevisionNoticesUseCase,
    private val getSafetyNoticesUseCase: GetSafetyNoticesUseCase,
    private val getScholarshipNoticesUseCase: GetScholarshipNoticesUseCase,
    private val getStudentActsNoticesUseCase: GetStudentActsNoticesUseCase,
    private val getTeachingNoticesUseCase: GetTeachingNoticesUseCase
): ViewModel() {

    private var _currentPage = MutableLiveData<Int>()
    val currentPage: LiveData<Int> get() = _currentPage

    var totalPage = 0
    var currentNotice = listOf<NoticeItem>()
    var searchNotice = listOf<NoticeItem>()

    private val _uiState = MutableLiveData<UiState<List<NoticeItem>>>(UiState.Loading)
    val uiState: LiveData<UiState<List<NoticeItem>>> get() = _uiState

    private val _searchState = MutableLiveData<UiState<List<NoticeItem>>>(UiState.Loading)
    val searchState: LiveData<UiState<List<NoticeItem>>> get() = _searchState

    fun fetchData(noticeType: NoticeType, page: Int, campus: String = "전체"){
        if(page > totalPage || page < 0) return

        _currentPage.value = page

        when (noticeType) {
            NoticeType.NORMAL_NOTICE -> normalNotice(page)
            NoticeType.EVENT_NOTICE -> eventNotice(page)
            NoticeType.ACADEMIC_NOTICE -> academicNotice(page)
            NoticeType.SCHOLARSHIP_NOTICE -> scholarshipNotice(page)
            NoticeType.CAREER_NOTICE -> careerNotice(page)
            NoticeType.STUDENT_ACTS_NOTICE -> studentActsNotice(page)
            NoticeType.BIDDING_NOTICE -> biddingNotice(page)
            NoticeType.SAFETY_NOTICE -> safetyNotice(page)
            NoticeType.REVISION_NOTICE -> revisionNotice(page)
            NoticeType.DORMITORY_NOTICE -> dormitoryNotice(page, campus)
            NoticeType.DORMITORY_ENTRY_NOTICE -> dormitoryEntryNotice(page, campus)
            NoticeType.LIBRARY_NOTICE -> libraryNotice(page, campus)
            NoticeType.TEACHING_NOTICE -> teachingNotice(page)
            NoticeType.JOB_TRAINING_NOTICE -> jobTrainingNotice(page)
            else -> dummyFunc()
        }
    }

    private fun dummyFunc(){}

    private var currentClass = "전체"
    fun classificationNotice(noticeType: NoticeType, page: Int, campus: String){
        if (currentClass != campus){
            currentClass = campus
            _currentPage.value = 0
            totalPage = 0
            currentNotice = emptyList()

            fetchData(noticeType, 0, campus)
        } else {
            fetchData(noticeType, page, campus)
        }
    }

    private val _toastState = MutableLiveData<UiState<String>>(UiState.Loading)
    val toastState: LiveData<UiState<String>> get() = _toastState

    fun postBookmark(category: NoticeType, noticeId: Int){
        viewModelScope.launch {
            postBookmarkUseCase.invoke(
                ssaId = application.getAndroidId(),
                category = category,
                noticeId = noticeId
            )
                .onFailure {
                    LoggerUtil.e("[${category.koName}] 북마크 실패: ${it.message}")
                    _toastState.value = UiState.Success("북마크 실패")
                }
                .onSuccess {
                    LoggerUtil.d("[${category.koName}] 북마크 성공")
                    _toastState.value = UiState.Success("북마크 성공")
                }

        }
    }

    fun deleteBookmark(category: NoticeType, noticeId: Int){
        viewModelScope.launch {
            deleteBookmarkUseCase.invoke(
                ssaId = application.getAndroidId(),
                category = category,
                noticeId = noticeId
            )
                .onFailure {
                    LoggerUtil.e("[${category.koName}] 북마크 삭제 실패: ${it.message}")
                    _toastState.value = UiState.Success("북마크 삭제 실패")
                }
                .onSuccess {
                    LoggerUtil.d("[${category.koName}] 북마크 삭제 성공")
                    _toastState.value = UiState.Success("북마크 삭제 성공")
                }
        }
    }

    fun searchNotice(noticeType: NoticeType, keyword: String){
        _searchState.value = UiState.Loading

        viewModelScope.launch {
            searchNoticesByCategoryUseCase.invoke(
                noticeType = noticeType,
                keyword = keyword,
                ssaId = application.getAndroidId()
            )
                .onFailure { _searchState.value = UiState.Error(it) }
                .onSuccess {
                    _searchState.value = if(it.isEmpty()) UiState.Empty else UiState.Success(it)
                }
        }
    }

    private fun normalNotice(page: Int){
        _uiState.value = UiState.Loading

        viewModelScope.launch {
            getNormalNoticesUseCase.invoke(
                page = page,
                ssaId = application.getAndroidId()
            )
                .onFailure {
                    _uiState.value = UiState.Error(it)
                }
                .onSuccess {
                    if(page == 0) totalPage = calculateTotalPages(it.totalItems, it.items.size)
                    _uiState.value = UiState.Success(it.items.ifEmpty { emptyList() })
                }
        }

    }


    private fun eventNotice(page: Int){
        _uiState.value = UiState.Loading

        viewModelScope.launch {
            getEventNoticesUseCase.invoke(
                page = page,
                ssaId = application.getAndroidId()
            )
                .onFailure {
                    _uiState.value = UiState.Error(it)
                }
                .onSuccess {
                    if(page == 0) totalPage = calculateTotalPages(it.totalItems, it.items.size)
                    _uiState.value = UiState.Success(it.items.ifEmpty { emptyList() })
                }
        }
    }

    private fun academicNotice(page: Int) {
        _uiState.value = UiState.Loading

        viewModelScope.launch {
            getAcademicNoticesUseCase.invoke(
                page = page,
                ssaId = application.getAndroidId()
            )
                .onFailure {
                    _uiState.value = UiState.Error(it)
                }
                .onSuccess {
                    if(page == 0) totalPage = calculateTotalPages(it.totalItems, it.items.size)
                    _uiState.value = UiState.Success(it.items.ifEmpty { emptyList() })
                }
        }
    }

    private fun scholarshipNotice(page: Int){
        _uiState.value = UiState.Loading

        viewModelScope.launch {
            getScholarshipNoticesUseCase.invoke(
                page = page,
                ssaId = application.getAndroidId()
            )
                .onFailure {
                    _uiState.value = UiState.Error(it)
                }
                .onSuccess {
                    if(page == 0) totalPage = calculateTotalPages(it.totalItems, it.items.size)
                    _uiState.value = UiState.Success(it.items.ifEmpty { emptyList() })
                }
        }
    }

    private fun careerNotice(page: Int){
        _uiState.value = UiState.Loading

        viewModelScope.launch {
            getCareerNoticesUseCase.invoke(
                page = page,
                ssaId = application.getAndroidId()
            )
                .onFailure {
                    _uiState.value = UiState.Error(it)
                }
                .onSuccess {
                    if(page == 0) totalPage = calculateTotalPages(it.totalItems, it.items.size)
                    _uiState.value = UiState.Success(it.items.ifEmpty { emptyList() })
                }
        }
    }

    private fun studentActsNotice(page: Int){
        _uiState.value = UiState.Loading

        viewModelScope.launch {
            getStudentActsNoticesUseCase.invoke(
                page = page,
                ssaId = application.getAndroidId()
            )
                .onFailure {
                    _uiState.value = UiState.Error(it)
                }
                .onSuccess {
                    if(page == 0) totalPage = calculateTotalPages(it.totalItems, it.items.size)
                    _uiState.value = UiState.Success(it.items.ifEmpty { emptyList() })
                }
        }
    }

    private fun biddingNotice(page: Int){
        _uiState.value = UiState.Loading

        viewModelScope.launch {
            getBiddingNoticesUseCase.invoke(
                page = page,
                ssaId = application.getAndroidId()
            )
                .onFailure {
                    _uiState.value = UiState.Error(it)
                }
                .onSuccess {
                    if(page == 0) totalPage = calculateTotalPages(it.totalItems, it.items.size)
                    _uiState.value = UiState.Success(it.items.ifEmpty { emptyList() })
                }
        }
    }

    private fun safetyNotice(page: Int){
        _uiState.value = UiState.Loading

        viewModelScope.launch {
            getSafetyNoticesUseCase.invoke(
                page = page,
                ssaId = application.getAndroidId()
            )
                .onFailure {
                    _uiState.value = UiState.Error(it)
                }
                .onSuccess {
                    if(page == 0) totalPage = calculateTotalPages(it.totalItems, it.items.size)
                    _uiState.value = UiState.Success(it.items.ifEmpty { emptyList() })
                }
        }

    }

    private fun revisionNotice(page: Int) {
        _uiState.value = UiState.Loading

        viewModelScope.launch {
            getRevisionNoticesUseCase.invoke(
                page = page,
                ssaId = application.getAndroidId()
            )
                .onFailure {
                    _uiState.value = UiState.Error(it)
                }
                .onSuccess {
                    if(page == 0) totalPage = calculateTotalPages(it.totalItems, it.items.size)
                    _uiState.value = UiState.Success(it.items.ifEmpty { emptyList() })
                }
        }
    }

    private fun dormitoryNotice(page: Int, campus: String){
        _uiState.value = UiState.Loading

        viewModelScope.launch {
            getDormitoryNoticesUseCase.invoke(
                page = page,
                ssaId = application.getAndroidId(),
                campus = campus
            )
                .onFailure {
                    _uiState.value = UiState.Error(it)
                }
                .onSuccess {
                    if(page == 0) totalPage = calculateTotalPages(totalItems = it.totalItems, firstPageItems = it.items.size)
                    _uiState.value = UiState.Success(it.items.ifEmpty { emptyList() })
                }
        }
    }

    private fun dormitoryEntryNotice(page: Int, campus: String){
        _uiState.value = UiState.Loading

        viewModelScope.launch {
            getDormitoryEntryNoticesUseCase.invoke(
                page = page,
                ssaId = application.getAndroidId(),
                campus = campus
            )
                .onFailure {
                    _uiState.value = UiState.Error(it)
                }
                .onSuccess {
                    if(page == 0) totalPage = calculateTotalPages(totalItems = it.totalItems, firstPageItems = it.items.size)
                    _uiState.value = UiState.Success(it.items.ifEmpty { emptyList() })
                }
        }
    }

    private fun libraryNotice(page: Int, campus: String){
        _uiState.value = UiState.Loading

        viewModelScope.launch {
            getLibraryNoticesUseCase.invoke(
                page = page,
                ssaId = application.getAndroidId(),
                campus = campus
            )
                .onFailure {
                    _uiState.value = UiState.Error(it)
                }
                .onSuccess {
                    if(page == 0) totalPage = calculateTotalPages(totalItems = it.totalItems, firstPageItems = it.items.size)
                    _uiState.value = UiState.Success(it.items.ifEmpty { emptyList() })
                }
        }
    }

    private fun teachingNotice(page: Int){
        _uiState.value = UiState.Loading

        viewModelScope.launch {
            getTeachingNoticesUseCase.invoke(
                page = page,
                ssaId = application.getAndroidId()
            )
                .onFailure {
                    _uiState.value = UiState.Error(it)
                }
                .onSuccess {
                    if(page == 0) totalPage = calculateTotalPages(totalItems = it.totalItems, firstPageItems = it.items.size)
                    _uiState.value = UiState.Success(it.items.ifEmpty { emptyList() })
                }
        }
    }

    private fun jobTrainingNotice(page: Int){
        _uiState.value = UiState.Loading

        viewModelScope.launch {
            getJobTrainingNoticesUseCase.invoke(
                page = page,
                ssaId = application.getAndroidId()
            )
                .onFailure {
                    _uiState.value = UiState.Error(it)
                }
                .onSuccess {
                    if(page == 0) totalPage = calculateTotalPages(totalItems = it.totalItems, firstPageItems = it.items.size)
                    _uiState.value = UiState.Success(it.items.ifEmpty { emptyList() })
                }
        }
    }

    private fun calculateTotalPages(totalItems: Int, firstPageItems: Int): Int {
        if (totalItems <= firstPageItems) {
            return 1
        }

        val remainingItems = totalItems - firstPageItems
        val subsequentPageItems = 10
        val additionalPages = ceil(remainingItems.toDouble() / subsequentPageItems).toInt()

        return 1 + additionalPages
    }
}