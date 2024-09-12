package com.neverland.thinkerbell.view.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neverland.domain.model.keyword.Keyword
import com.neverland.domain.usecase.alarm.GetAlarmStatusUseCase
import com.neverland.domain.usecase.alarm.PatchAlarmStatusUseCase
import com.neverland.domain.usecase.keyword.GetKeywordUseCase
import com.neverland.thinkerbell.base.ThinkerBellApplication.Companion.application
import com.neverland.thinkerbell.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val getKeywordUseCase: GetKeywordUseCase,
    private val getAlarmStatusUseCase: GetAlarmStatusUseCase,
    private val patchAlarmStatusUseCase: PatchAlarmStatusUseCase
) : ViewModel() {

    private val _keyword = MutableLiveData<UiState<List<Keyword>>>(UiState.Loading)
    val keyword: LiveData<UiState<List<Keyword>>> get() = _keyword

    private val _uiState = MutableLiveData<UiState<Boolean>>(UiState.Loading)
    val uiState: LiveData<UiState<Boolean>> get() = _uiState

    private val _alarmStatus = MutableLiveData<UiState<Unit>>(UiState.Loading)
    val alarmStatus: LiveData<UiState<Unit>> get() = _alarmStatus

    fun fetchKeyword() {
        _keyword.value = UiState.Loading
        viewModelScope.launch {
            getKeywordUseCase.invoke(application.getAndroidId())
                .onSuccess { keyword ->
                    _keyword.value = UiState.Success(keyword)
                }
                .onFailure { exception ->
                    _keyword.value = UiState.Error(exception)
                }
        }
    }

    fun fetchAlarmStatus(){
        _uiState.value = UiState.Loading

        viewModelScope.launch {
            getAlarmStatusUseCase(application.getAndroidId())
                .onSuccess {
                    _uiState.value = UiState.Success(it)
                }
                .onFailure {
                    _uiState.value = UiState.Error(it)
                }
        }
    }

    fun patchAlarmStatus(){
        _alarmStatus.value = UiState.Loading

        viewModelScope.launch {
            patchAlarmStatusUseCase(application.getAndroidId())
                .onSuccess {
                    _alarmStatus.value = UiState.Success(Unit)
                }
                .onFailure {
                    _alarmStatus.value = UiState.Error(it)
                }
        }
    }
}