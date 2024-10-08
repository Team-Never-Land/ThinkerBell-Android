package com.neverland.thinkerbell.view.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neverland.domain.model.keyword.Keyword
import com.neverland.domain.usecase.keyword.DeleteKeywordUseCase
import com.neverland.domain.usecase.keyword.GetKeywordUseCase
import com.neverland.domain.usecase.keyword.PostKeywordUseCase
import com.neverland.thinkerbell.base.ThinkerBellApplication.Companion.application
import com.neverland.thinkerbell.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KeywordViewModel @Inject constructor(
    private val getKeywordUseCase: GetKeywordUseCase,
    private val postKeywordUseCase: PostKeywordUseCase,
    private val deleteKeywordUseCase: DeleteKeywordUseCase,
) : ViewModel() {

    private val _keyword = MutableLiveData<UiState<List<Keyword>>>(UiState.Loading)
    val keyword: LiveData<UiState<List<Keyword>>> get() = _keyword

    private val _postState = MutableLiveData<UiState<String>>(UiState.Loading)
    val postState: LiveData<UiState<String>> get() = _postState

    private val _deleteState = MutableLiveData<UiState<String>>(UiState.Loading)
    val deleteState: LiveData<UiState<String>> get() = _deleteState

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

    fun postKeyword(keyword: String) {
        _postState.value = UiState.Loading
        viewModelScope.launch {
            postKeywordUseCase.invoke(ssaId = application.getAndroidId(), keyword = keyword)
                .onSuccess { _postState.value = UiState.Success("등록 되었습니다") }
                .onFailure { _postState.value = UiState.Error(it) }
        }
    }

    fun deleteKeyword(keyword: String) {
        _deleteState.value = UiState.Loading
        viewModelScope.launch {
            deleteKeywordUseCase.invoke(ssaId = application.getAndroidId(), keyword = keyword)
                .onSuccess { _deleteState.value = UiState.Success("삭제 되었습니다") }
                .onFailure { _deleteState.value = UiState.Error(it) }
        }
    }
}