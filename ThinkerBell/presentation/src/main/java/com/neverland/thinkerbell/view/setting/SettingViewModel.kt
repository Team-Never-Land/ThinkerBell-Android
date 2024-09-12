package com.neverland.thinkerbell.view.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neverland.domain.model.keyword.Keyword
import com.neverland.domain.usecase.keyword.GetKeywordUseCase
import com.neverland.thinkerbell.base.ThinkerBellApplication.Companion.application
import com.neverland.thinkerbell.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val getKeywordUseCase: GetKeywordUseCase,
) : ViewModel() {

    private val _keyword = MutableLiveData<UiState<List<Keyword>>>(UiState.Loading)
    val keyword: LiveData<UiState<List<Keyword>>> get() = _keyword

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
}