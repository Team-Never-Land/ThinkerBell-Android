package com.neverland.thinkerbell.view.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neverland.core.utils.LoggerUtil
import com.neverland.domain.enums.NoticeType
import com.neverland.domain.usecase.bookmark.DeleteBookmarkUseCase
import com.neverland.domain.usecase.bookmark.PostBookmarkUseCase
import com.neverland.thinkerbell.base.ThinkerBellApplication
import com.neverland.thinkerbell.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchResultNoticeViewModel @Inject constructor(
    private val postBookmarkUseCase: PostBookmarkUseCase,
    private val deleteBookmarkUseCase: DeleteBookmarkUseCase
) : ViewModel() {

    private val _toastState = MutableLiveData<UiState<String>>(UiState.Loading)
    val toastState: LiveData<UiState<String>> get() = _toastState

    fun postBookmark(category: NoticeType, noticeId: Int) {
        viewModelScope.launch {
            postBookmarkUseCase.invoke(
                ssaId = ThinkerBellApplication.application.getAndroidId(),
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

    fun deleteBookmark(category: NoticeType, noticeId: Int) {
        viewModelScope.launch {
            deleteBookmarkUseCase.invoke(
                ssaId = ThinkerBellApplication.application.getAndroidId(),
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