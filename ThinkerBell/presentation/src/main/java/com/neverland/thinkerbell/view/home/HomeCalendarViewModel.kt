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

    // 연도와 월을 기반으로 데이터를 가져오는 함수로 수정
    fun fetchData(year: Int, month: Int) {
        _uiState.value = UiState.Loading

        viewModelScope.launch {
            // UseCase에 연도와 월을 함께 전달하도록 수정
            getAcademicScheduleUseCase.invoke(year, month, ssaId = application.getAndroidId())
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

    fun postBookmark(noticeId: Int) {
        val category = NoticeType.ACADEMIC_SCHEDULE

        viewModelScope.launch {
            postBookmarkUseCase.invoke(
                ssaId = application.getAndroidId(),
                category = category,
                noticeId = noticeId
            )
                .onFailure {
                    LoggerUtil.e("[${category.koName}] 즐겨찾기 실패: ${it.message}")
                    _toastState.value = UiState.Success("즐겨찾기 실패")
                }
                .onSuccess {
                    LoggerUtil.d("[${category.koName}] 즐겨찾기 성공")
                    _toastState.value = UiState.Success("즐겨찾기 되었습니다")
                }
        }
    }

    fun deleteBookmark(noticeId: Int) {
        val category = NoticeType.ACADEMIC_SCHEDULE

        viewModelScope.launch {
            deleteBookmarkUseCase.invoke(
                ssaId = application.getAndroidId(),
                category = category,
                noticeId = noticeId
            )
                .onFailure {
                    LoggerUtil.e("[${category.koName}] 즐겨찾기 삭제 실패: ${it.message}")
                    _toastState.value = UiState.Success("즐겨찾기 삭제 실패")
                }
                .onSuccess {
                    LoggerUtil.d("[${category.koName}] 즐겨찾기 삭제 성공")
                    _toastState.value = UiState.Success("삭제 되었습니다")
                }
        }
    }
}
