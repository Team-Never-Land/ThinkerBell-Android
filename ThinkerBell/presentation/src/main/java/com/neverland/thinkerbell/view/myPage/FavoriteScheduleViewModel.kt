package com.neverland.thinkerbell.view.myPage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neverland.core.utils.LoggerUtil
import com.neverland.domain.enums.NoticeType
import com.neverland.domain.model.notice.NoticeItem
import com.neverland.domain.model.univ.AcademicSchedule
import com.neverland.domain.usecase.bookmark.DeleteBookmarkUseCase
import com.neverland.domain.usecase.bookmark.GetBookmarkScheduleUseCase
import com.neverland.domain.usecase.bookmark.PostBookmarkUseCase
import com.neverland.thinkerbell.base.ThinkerBellApplication
import com.neverland.thinkerbell.base.ThinkerBellApplication.Companion.application
import com.neverland.thinkerbell.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteScheduleViewModel @Inject constructor(
    private val getBookmarkScheduleUseCase: GetBookmarkScheduleUseCase,
    private val postBookmarkUseCase: PostBookmarkUseCase,
    private val deleteBookmarkUseCase: DeleteBookmarkUseCase
) : ViewModel() {

    private val _schedules = MutableLiveData<UiState<List<AcademicSchedule>>>(UiState.Loading)
    val schedules: LiveData<UiState<List<AcademicSchedule>>> get() = _schedules

    private val _bookmarkState = MutableLiveData<UiState<String>>(UiState.Loading)
    val bookmarkState: LiveData<UiState<String>> get() = _bookmarkState


    init {
        fetchNotices()
    }

    private fun fetchNotices() {
        viewModelScope.launch {
            getBookmarkScheduleUseCase.invoke(application.getAndroidId())
                .onSuccess { bookmarkSchedule ->
                    _schedules.value = UiState.Success(bookmarkSchedule)
                }
                .onFailure { exception ->
                    _schedules.value = UiState.Error(exception)
                }
        }
    }

    fun postBookmark(category: NoticeType, noticeId: Int){
        _bookmarkState.value = UiState.Loading
        viewModelScope.launch {
            postBookmarkUseCase.invoke(
                ssaId = ThinkerBellApplication.application.getAndroidId(),
                category = category,
                noticeId = noticeId
            )
                .onFailure {
                    LoggerUtil.e("[${category.koName}] 즐겨찾기 실패: ${it.message}")
                    _bookmarkState.value = UiState.Success("즐겨찾기 실패")
                }
                .onSuccess {
                    LoggerUtil.d("[${category.koName}] 즐겨찾기 성공")
                    _bookmarkState.value = UiState.Success("즐겨찾기 되었습니다")
                }

        }
    }

    fun deleteBookmark(category: NoticeType, noticeId: Int){
        _bookmarkState.value = UiState.Loading
        viewModelScope.launch {
            deleteBookmarkUseCase.invoke(
                ssaId = ThinkerBellApplication.application.getAndroidId(),
                category = category,
                noticeId = noticeId
            )
                .onFailure {
                    LoggerUtil.e("[${category.koName}] 즐겨찾기 삭제 실패: ${it.message}")
                    _bookmarkState.value = UiState.Success("즐겨찾기 삭제 실패")
                }
                .onSuccess {
                    LoggerUtil.d("[${category.koName}] 즐겨찾기 삭제 성공")
                    _bookmarkState.value = UiState.Success("삭제 되었습니다")
                }
        }
    }

}