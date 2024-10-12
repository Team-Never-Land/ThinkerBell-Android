package com.neverland.thinkerbell.view.alarm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neverland.core.utils.LoggerUtil
import com.neverland.domain.enums.NoticeType
import com.neverland.domain.model.alarm.Alarm
import com.neverland.domain.usecase.alarm.GetAlarmUseCase
import com.neverland.domain.usecase.alarm.ReadAlarmUseCase
import com.neverland.domain.usecase.bookmark.DeleteBookmarkUseCase
import com.neverland.domain.usecase.bookmark.PostBookmarkUseCase
import com.neverland.thinkerbell.base.ThinkerBellApplication
import com.neverland.thinkerbell.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmNoticeViewModel @Inject constructor(
    private val getAlarmUseCase: GetAlarmUseCase,
    private val readAlarmUseCase: ReadAlarmUseCase,
    private val postBookmarkNoticeUseCase: PostBookmarkUseCase,
    private val deleteBookmarkNoticeUseCase: DeleteBookmarkUseCase
) : ViewModel() {

    private val _alarmNotices = MutableLiveData<UiState<List<Alarm>>>(UiState.Loading)
    val alarmNotices: LiveData<UiState<List<Alarm>>> get() = _alarmNotices

    private val _readAlarm = MutableLiveData<UiState<String>>(UiState.Loading)
    val readAlarm: LiveData<UiState<String>> get() = _readAlarm

    private val _bookmarkState = MutableLiveData<UiState<String>>(UiState.Loading)
    val bookmarkState: LiveData<UiState<String>> get() = _bookmarkState

    fun fetchAlarmNotices(keyword: String) {
        _alarmNotices.value = UiState.Loading
        viewModelScope.launch {
            getAlarmUseCase.invoke(
                ssaId = ThinkerBellApplication.application.getAndroidId(),
                keyword = keyword
            )
                .onSuccess { alarmNotices ->
                    _alarmNotices.value = UiState.Success(alarmNotices)
                }
                .onFailure { exception ->
                    _alarmNotices.value = UiState.Error(exception)
                }
        }
    }

    fun readAlarmNotice(alarmId: Int) {
        _readAlarm.value = UiState.Loading
        viewModelScope.launch {
            readAlarmUseCase.invoke(alarmId = alarmId)
                .onSuccess { readAlarm ->
                    _readAlarm.value = UiState.Success(readAlarm)
                }
                .onFailure { exception ->
                    _readAlarm.value = UiState.Error(exception)
                }
        }
    }

    fun postBookmark(category: NoticeType, noticeId: Int) {
        _bookmarkState.value = UiState.Loading
        viewModelScope.launch {
            postBookmarkNoticeUseCase.invoke(
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

    fun deleteBookmark(category: NoticeType, noticeId: Int) {
        _bookmarkState.value = UiState.Loading
        viewModelScope.launch {
            deleteBookmarkNoticeUseCase.invoke(
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