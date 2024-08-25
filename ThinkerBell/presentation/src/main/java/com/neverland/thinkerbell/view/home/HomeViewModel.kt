package com.neverland.thinkerbell.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neverland.domain.usecase.alarm.CheckAllAlarmUseCase
import com.neverland.domain.usecase.notice.GetRecentNoticesUseCase
import com.neverland.domain.usecase.univ.GetBannerUseCase
import com.neverland.domain.model.notice.RecentNotices
import com.neverland.domain.model.univ.Banner
import com.neverland.thinkerbell.base.ThinkerBellApplication.Companion.application
import com.neverland.thinkerbell.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getBannerUseCase: GetBannerUseCase,
    private val getRecentNoticesUseCase: GetRecentNoticesUseCase,
    private val checkAllAlarmUseCase: CheckAllAlarmUseCase
) : ViewModel() {


    private val _banners = MutableLiveData<UiState<List<Banner>>>()
    val banners: LiveData<UiState<List<Banner>>> get() = _banners

    private val _uiState = MutableLiveData<UiState<RecentNotices>>()
    val uiState: LiveData<UiState<RecentNotices>> get() = _uiState

    private val _alarmState = MutableLiveData<UiState<Boolean>>()
    val alarmState: LiveData<UiState<Boolean>> get() = _alarmState

    fun fetchBanners() {
        _banners.value = UiState.Loading

        viewModelScope.launch {
            getBannerUseCase.invoke()
                .onSuccess { banner ->
                    _banners.value = UiState.Success(banner)
                }.onFailure { exception ->
                    _banners.value = UiState.Error(exception)
                }
        }
    }

    fun fetchRecentNotices() {
        _uiState.value = UiState.Loading

        viewModelScope.launch {
            getRecentNoticesUseCase.invoke(application.getAndroidId())
                .onSuccess { _uiState.value = UiState.Success(it) }
                .onFailure { _uiState.value = UiState.Error(it) }
        }
    }

    fun checkAllAlarm() {
        _alarmState.value = UiState.Loading

        viewModelScope.launch {
            checkAllAlarmUseCase.invoke(application.getAndroidId())
                .onSuccess { _alarmState.value = UiState.Success(it) }
                .onFailure { _alarmState.value = UiState.Error(it) }
        }
    }
}