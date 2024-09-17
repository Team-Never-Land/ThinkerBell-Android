package com.neverland.thinkerbell.view.myPage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neverland.domain.model.notice.RecentBookmarkNotice
import com.neverland.domain.model.univ.RecentBookmarkSchedule
import com.neverland.domain.usecase.bookmark.GetRecentBookmarkNoticeUseCase
import com.neverland.domain.usecase.bookmark.GetRecentBookmarkScheduleUseCase
import com.neverland.thinkerbell.base.ThinkerBellApplication.Companion.application
import com.neverland.thinkerbell.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val getRecentBookmarkNoticeUseCase: GetRecentBookmarkNoticeUseCase,
    private val getRecentBookmarkScheduleUseCase: GetRecentBookmarkScheduleUseCase,
) : ViewModel() {

    private val _recentFavoriteNotices = MutableLiveData<UiState<List<RecentBookmarkNotice>>>(
        UiState.Loading
    )
    val recentFavoriteNotices: LiveData<UiState<List<RecentBookmarkNotice>>> get() = _recentFavoriteNotices

    private val _recentFavoriteSchedules = MutableLiveData<UiState<List<RecentBookmarkSchedule>>>(
        UiState.Loading
    )
    val recentFavoriteSchedules: LiveData<UiState<List<RecentBookmarkSchedule>>> get() = _recentFavoriteSchedules


    init {
        fetchFavoriteNotices()
        fetchFavoriteSchedules()
    }

    fun fetchFavoriteNotices() {
        _recentFavoriteNotices.value = UiState.Loading
        viewModelScope.launch {
            getRecentBookmarkNoticeUseCase.invoke(application.getAndroidId())
                .onSuccess { bookmarkNotice ->
                    _recentFavoriteNotices.value = UiState.Success(bookmarkNotice)
                }
                .onFailure { exception ->
                    _recentFavoriteNotices.value = UiState.Error(exception)
                }
        }
    }

    fun fetchFavoriteSchedules() {
        _recentFavoriteSchedules.value = UiState.Loading
        viewModelScope.launch {
            getRecentBookmarkScheduleUseCase.invoke(application.getAndroidId())
                .onSuccess { bookmarkSchedule ->
                    _recentFavoriteSchedules.value = UiState.Success(bookmarkSchedule)
                }
                .onFailure { exception ->
                    _recentFavoriteNotices.value = UiState.Error(exception)
                }
        }
    }

}