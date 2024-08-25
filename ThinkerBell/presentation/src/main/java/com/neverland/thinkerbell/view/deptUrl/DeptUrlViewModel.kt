package com.neverland.thinkerbell.view.deptUrl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neverland.domain.model.group.Group
import com.neverland.domain.model.univ.DeptUrl
import com.neverland.domain.usecase.univ.GetDeptUrlUseCase
import com.neverland.thinkerbell.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeptUrlViewModel @Inject constructor(
    private val getDeptUrlUseCase: GetDeptUrlUseCase
) : ViewModel() {


    private val _uiState = MutableLiveData<UiState<List<Group<DeptUrl>>>>(UiState.Loading)
    val uiState: LiveData<UiState<List<Group<DeptUrl>>>> get() = _uiState


    fun fetchData() {
        _uiState.value = UiState.Loading

        viewModelScope.launch {
            getDeptUrlUseCase.invoke()
                .onSuccess {
                    val groups = mutableListOf<Group<DeptUrl>>()
                    it.forEach { (key, value) ->
                        groups.add(Group(name = key, subGroups = emptyList(), items = value))
                    }

                    _uiState.value = UiState.Success(groups)
                }
                .onFailure {
                    _uiState.value = UiState.Error(it)
                }
        }
    }
}