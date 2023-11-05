package com.khomenok.monitorschedule.presentation.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khomenok.monitorschedule.domain.models.StateStatus
import com.khomenok.monitorschedule.domain.models.Group
import com.khomenok.monitorschedule.domain.usecase.GetFacultyUseCase
import com.khomenok.monitorschedule.domain.usecase.GetGroupItemsUseCase
import com.khomenok.monitorschedule.domain.usecase.GetSpecialityUseCase
import com.khomenok.monitorschedule.domain.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GroupItemsViewModel(
    private val getGroupItemsUseCase: GetGroupItemsUseCase,
    private val getFacultyUseCase: GetFacultyUseCase,
    private val getSpecialityUseCase: GetSpecialityUseCase
) : ViewModel() {

    private val allGroupItemsLoading = MutableLiveData(false)
    private val isUpdating = MutableLiveData(false)
    private val error = MutableLiveData<StateStatus>(null)
    private val allGroupItems = MutableLiveData<ArrayList<Group>>(null)
    val isUpdatingStatus = isUpdating
    val errorStatus = error
    val allGroupItemsStatus = allGroupItems

    fun closeError() {
        error.value = null
    }

    fun saveGroupItem(group: Group) {
        viewModelScope.launch(Dispatchers.IO) {
            getGroupItemsUseCase.saveGroupItems(arrayListOf(group))
        }
    }

    private suspend fun updateFaculties() {
        when (
            val result = getFacultyUseCase.getFacultiesAPI()
        ) {
            is Resource.Success -> {
                val savedFaculties = getFacultyUseCase.saveFaculties(result.data!!)
                // onSuccess
            }
            is Resource.Error -> {
                // if error, get state and get message of error
            }
        }
    }

    private suspend fun updateSpecialities() {
        when (
            val result = getSpecialityUseCase.getSpecialitiesAPI()
        ) {
            is Resource.Success -> {
            val savedSpecialities = getSpecialityUseCase.saveSpecialities(result.data!!)
            }
            is Resource.Error -> {
            }
        }
    }

    fun updateInitDataAndGroups() {
        viewModelScope.launch(Dispatchers.IO) {
            isUpdating.postValue(true)
            val initData = launch(Dispatchers.IO) {
                updateSpecialities()
                updateFaculties()
            }
            initData.join()
            updateGroupItems()
            isUpdating.postValue(false)
        }
    }

    // Update all group items from API
    private suspend fun updateGroupItems() {
        when (
            val newGroupItems = getGroupItemsUseCase.getGroupItemsAPI()
        ) {
            is Resource.Success -> {
                when (
                    getGroupItemsUseCase.saveGroupItems(newGroupItems.data!!)
                ) {
                    is Resource.Success -> {
                        getAllGroupItems()
                    }
                    is Resource.Error -> {
                        error.postValue(StateStatus(
                            state = StateStatus.ERROR_STATE,
                            type = newGroupItems.statusCode,
                            message = newGroupItems.message
                        ))
                    }
                }
            }
            is Resource.Error -> {
                error.postValue(StateStatus(
                    state = StateStatus.ERROR_STATE,
                    type = newGroupItems.statusCode,
                    message = newGroupItems.message
                ))
            }
        }
    }

    fun filterByKeyword(s: String, isAsc: Boolean = true) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = if (isAsc) {
                getGroupItemsUseCase.filterByKeywordASC(s)
            } else {
                getGroupItemsUseCase.filterByKeywordDESC(s)
            }
            when(result) {
                is Resource.Success -> {
                    allGroupItems.postValue(result.data)
                }
                is Resource.Error -> {
                    error.postValue(StateStatus(
                        state = StateStatus.ERROR_STATE,
                        type = result.statusCode,
                        message = result.message
                    ))
                }
            }
        }
    }

    // All Group Items
    fun getAllGroupItems() {
        viewModelScope.launch(Dispatchers.IO) {
            allGroupItemsLoading.postValue(true)
            when (
                val result = getGroupItemsUseCase.getAllGroupItems()
            ) {
                is Resource.Success -> {
                    allGroupItems.postValue(result.data)
                    if (result.data.isNullOrEmpty()) {
                        allGroupItems.postValue(null)
                    }
                }
                is Resource.Error -> {
                    error.postValue(StateStatus(
                        state = StateStatus.ERROR_STATE,
                        type = result.statusCode,
                        message = result.message
                    ))
                }
            }
            allGroupItemsLoading.postValue(false)
        }
    }

}