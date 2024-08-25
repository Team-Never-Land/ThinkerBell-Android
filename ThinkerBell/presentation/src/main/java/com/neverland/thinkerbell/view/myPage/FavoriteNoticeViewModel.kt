package com.neverland.thinkerbell.view.myPage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neverland.domain.enums.NoticeType
import com.neverland.domain.usecase.bookmark.DeleteBookmarkUseCase
import com.neverland.domain.usecase.bookmark.PostBookmarkUseCase
import com.neverland.thinkerbell.base.ThinkerBellApplication.Companion.application
import com.neverland.thinkerbell.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteNoticeViewModel @Inject constructor(
    private val postBookmarkNoticeUseCase: PostBookmarkUseCase,
    private val deleteBookmarkNoticeUseCase: DeleteBookmarkUseCase
) : ViewModel() {


    private val _bookmarkState = MutableLiveData<UiState<Unit>>(UiState.Loading)
    val bookmarkState: LiveData<UiState<Unit>> get() = _bookmarkState

    fun postBookmark(noticeId: Int, noticeType: NoticeType) {
        _bookmarkState.value = UiState.Loading

        viewModelScope.launch {
            postBookmarkNoticeUseCase.invoke(
                noticeId = noticeId,
                category = noticeType,
                ssaId = application.getAndroidId()
            )
                .onSuccess {
                    _bookmarkState.value = UiState.Success(Unit)
                }
                .onFailure { exception ->
                    _bookmarkState.value = UiState.Error(exception)
                }
        }
    }

    fun deleteBookmark(noticeId: Int, noticeType: NoticeType) {
        _bookmarkState.value = UiState.Loading

        viewModelScope.launch {
            deleteBookmarkNoticeUseCase.invoke(
                noticeId = noticeId,
                category = noticeType,
                ssaId = application.getAndroidId()
            )
                .onSuccess {
                    _bookmarkState.value = UiState.Success(Unit)
                }
                .onFailure { exception ->
                    _bookmarkState.value = UiState.Error(exception)
                }
        }
    }

}