package com.neverland.thinkerbell.view.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neverland.domain.usecase.report.PostErrorReportUseCase
import com.neverland.thinkerbell.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ErrorReportViewModel @Inject constructor(
    private val postErrorReportUseCase: PostErrorReportUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData<UiState<String>>(UiState.Loading)
    val uiState: LiveData<UiState<String>> get() = _uiState

    fun postErrorReport(errorMsg: String) {
        _uiState.value = UiState.Loading

        viewModelScope.launch {
            postErrorReportUseCase(errorMsg)
                .onSuccess {
                    _uiState.value = UiState.Success("신고되었습니다")
                }
                .onFailure {
                    _uiState.value = UiState.Error(it)
                }
        }
    }
}