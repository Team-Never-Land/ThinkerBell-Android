package com.neverland.thinkerbell.view.contact

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neverland.domain.model.group.Group
import com.neverland.domain.model.group.SubGroup
import com.neverland.domain.model.univ.DeptContact
import com.neverland.domain.usecase.univ.GetDeptContactUseCase
import com.neverland.thinkerbell.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val getDeptContactUseCase: GetDeptContactUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData<UiState<List<Group<DeptContact>>>>(UiState.Loading)
    val uiState: LiveData<UiState<List<Group<DeptContact>>>> get() = _uiState

    fun fetchData() {
        _uiState.value = UiState.Loading

        viewModelScope.launch {
            getDeptContactUseCase.invoke()
                .onSuccess { deptContacts ->
                    val groups = mutableListOf<Group<DeptContact>>()
                    deptContacts.forEach { (campus, colleges) ->
                        val subGroups = colleges.map { (college, contacts) ->
                            SubGroup(
                                name = college,
                                items = contacts.map { deptContact ->
                                    DeptContact(
                                        major = deptContact.major,
                                        contact = deptContact.contact,
                                        college = deptContact.college,
                                        campus = deptContact.campus
                                    )
                                }
                            )
                        }
                        groups.add(Group(name = campus, subGroups = subGroups))
                    }

                    _uiState.value = UiState.Success(groups)
                }
                .onFailure {
                    _uiState.value = UiState.Error(it)
                }
        }
    }
}
