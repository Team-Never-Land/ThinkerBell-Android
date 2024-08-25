package com.neverland.thinkerbell.view.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neverland.core.utils.LoggerUtil
import com.neverland.data.datasource.DataStoreLocaleDataSource
import com.neverland.domain.enums.NoticeType
import com.neverland.thinkerbell.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val dataStore: DataStoreLocaleDataSource
) : ViewModel() {

    private val _uiState = MutableLiveData<UiState<List<NoticeType>>>(UiState.Loading)
    val uiState: LiveData<UiState<List<NoticeType>>> get() = _uiState


    fun fetchData() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                dataStore.readCategoryOrder().collect { list ->
                    val result = list.ifEmpty { NoticeType.entries }.toMutableList()
                    result.remove(NoticeType.ACADEMIC_SCHEDULE)
                    _uiState.value = UiState.Success(result)
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e)
            }
        }
    }

    fun saveCategoryOrder(list: List<NoticeType>) {
        viewModelScope.launch {
            dataStore.saveCategoryOrder(list)
                .onFailure {
                    LoggerUtil.d("카테고리 순서 실패 완료")
                }
                .onSuccess {
                    LoggerUtil.d("카테고리 순서 저장 완료")
                }
        }
    }
}