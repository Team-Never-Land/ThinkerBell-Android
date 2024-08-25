package com.neverland.thinkerbell.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neverland.core.utils.LoggerUtil
import com.neverland.domain.enums.NoticeType
import com.neverland.domain.model.univ.AcademicSchedule
import com.neverland.domain.usecase.bookmark.DeleteBookmarkUseCase
import com.neverland.domain.usecase.bookmark.PostBookmarkUseCase
import com.neverland.domain.usecase.univ.GetAcademicScheduleUseCase
import com.neverland.thinkerbell.base.ThinkerBellApplication.Companion.application
import com.neverland.thinkerbell.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeCalendarViewModel @Inject constructor(
    private val getAcademicScheduleUseCase: GetAcademicScheduleUseCase,
    private val postBookmarkUseCase: PostBookmarkUseCase,
    private val deleteBookmarkUseCase: DeleteBookmarkUseCase
): ViewModel() {


    private val _uiState = MutableLiveData<UiState<List<AcademicSchedule>>>(UiState.Loading)
    val uiState: LiveData<UiState<List<AcademicSchedule>>> get() = _uiState

    fun fetchData(month: Int) {
        _uiState.value = UiState.Loading

        viewModelScope.launch {
            getAcademicScheduleUseCase.invoke(month, ssaId = application.getAndroidId())
                .onSuccess { schedules ->
                    _uiState.value = UiState.Success(schedules)
                }
                .onFailure { exception ->
                    _uiState.value = UiState.Error(exception)
                }
        }
    }

    private val _toastState = MutableLiveData<UiState<String>>(UiState.Loading)
    val toastState: LiveData<UiState<String>> get() = _toastState

    fun postBookmark(noticeId: Int){
        val category = NoticeType.ACADEMIC_SCHEDULE

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

    fun deleteBookmark(noticeId: Int){
        val category = NoticeType.ACADEMIC_SCHEDULE

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
}