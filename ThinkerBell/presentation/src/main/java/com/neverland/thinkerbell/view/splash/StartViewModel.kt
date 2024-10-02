package com.neverland.thinkerbell.view.splash

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neverland.core.utils.LoggerUtil
import com.neverland.domain.usecase.user.PostUserInfoUseCase
import com.neverland.domain.usecase.version.GetForceUpdateMinVersionUseCase
import com.neverland.thinkerbell.base.ThinkerBellApplication.Companion.application
import com.neverland.thinkerbell.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val postUserInfoUseCase: PostUserInfoUseCase,
    private val getForceUpdateMinVersionUseCase: GetForceUpdateMinVersionUseCase
) : ViewModel() {

    private val _fcmState = MutableLiveData<UiState<Unit>>(UiState.Loading)
    val fcmState: LiveData<UiState<Unit>> get() = _fcmState

    private val _update = MutableLiveData<UiState<Boolean>>()
    val update: LiveData<UiState<Boolean>> get() = _update

    @SuppressLint("HardwareIds")
    fun saveDeviceInfo(token: String) {
        _fcmState.value = UiState.Loading

        viewModelScope.launch {
            postUserInfoUseCase(application.getAndroidId(), token)
                .onSuccess {
                    _fcmState.value = UiState.Success(Unit)
                }
                .onFailure { e ->
                    _fcmState.value = UiState.Error(e)
                    LoggerUtil.e("Register fcm failed: ${e.message}")
                }
        }
    }

    fun checkForceUpdate(currentVersion: Int){
        _update.value = UiState.Loading

        viewModelScope.launch {
            getForceUpdateMinVersionUseCase.invoke()
                .onSuccess {
                    LoggerUtil.i("현재 버전: $currentVersion | 최소 업데이트 필요 버전: $it")
                    _update.value = UiState.Success(currentVersion < it.toInt())
                }
                .onFailure { _update.value = UiState.Success(false) }
        }
    }

}