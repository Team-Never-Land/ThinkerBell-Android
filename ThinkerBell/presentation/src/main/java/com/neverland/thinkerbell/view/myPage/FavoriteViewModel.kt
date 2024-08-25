package com.neverland.thinkerbell.view.myPage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neverland.domain.enums.NoticeType
import com.neverland.domain.model.notice.NoticeItem
import com.neverland.domain.usecase.bookmark.GetBookmarkNoticeUseCase
import com.neverland.thinkerbell.base.ThinkerBellApplication.Companion.application
import com.neverland.thinkerbell.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getBookmarkNoticeUseCase: GetBookmarkNoticeUseCase
) : ViewModel() {


    private val _notices = MutableLiveData<UiState<Map<NoticeType, List<NoticeItem>>>>(UiState.Loading)
    val notices: LiveData<UiState<Map<NoticeType, List<NoticeItem>>>> get() = _notices

    init {
        fetchNotices(application.getAndroidId())
    }

    private fun fetchNotices(ssaId: String) {
        viewModelScope.launch {
            getBookmarkNoticeUseCase.invoke(ssaId)
                .onSuccess { bookmarkNotice ->
                    _notices.value = UiState.Success(bookmarkNotice)
                }
                .onFailure { exception ->
                    _notices.value = UiState.Error(exception)
                }
        }
    }
}