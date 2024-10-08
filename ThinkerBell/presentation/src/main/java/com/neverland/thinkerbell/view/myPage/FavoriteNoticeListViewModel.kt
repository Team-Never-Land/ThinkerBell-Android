package com.neverland.thinkerbell.view.myPage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neverland.core.utils.LoggerUtil
import com.neverland.domain.enums.NoticeType
import com.neverland.domain.usecase.bookmark.DeleteBookmarkUseCase
import com.neverland.domain.usecase.bookmark.PostBookmarkUseCase
import com.neverland.thinkerbell.base.ThinkerBellApplication.Companion.application
import com.neverland.thinkerbell.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteNoticeListViewModel @Inject constructor(
    private val postBookmarkUseCase: PostBookmarkUseCase,
    private val deleteBookmarkUseCase: DeleteBookmarkUseCase,
) : ViewModel() {


    private val _bookmarkState = MutableLiveData<UiState<Unit>>(UiState.Loading)
    val bookmarkState: LiveData<UiState<Unit>> get() = _bookmarkState

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
                    LoggerUtil.e("[${category.koName}] 즐겨찾기 실패: ${it.message}")
                    _toastState.value = UiState.Success("즐겨찾기 실패")
                }
                .onSuccess {
                    LoggerUtil.d("[${category.koName}] 즐겨찾기 성공")
                    _toastState.value = UiState.Success("즐겨찾기 되었습니다")
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