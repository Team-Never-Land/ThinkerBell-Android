package com.neverland.thinkerbell.view.splash

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neverland.core.utils.LoggerUtil
import com.neverland.domain.usecase.user.PostUserInfoUseCase
import com.neverland.thinkerbell.base.ThinkerBellApplication.Companion.application
import com.neverland.thinkerbell.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val postUserInfoUseCase: PostUserInfoUseCase
) : ViewModel() {

    private val _fcmState = MutableLiveData<UiState<Unit>>(UiState.Loading)
    val fcmState: LiveData<UiState<Unit>> get() = _fcmState

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

}