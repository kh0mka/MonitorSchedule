package com.khomenok.monitorschedule.presentation.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khomenok.monitorschedule.domain.models.Schedule
import com.khomenok.monitorschedule.domain.models.ScheduleUpdatedAction
import com.khomenok.monitorschedule.domain.models.StateStatus
import com.khomenok.monitorschedule.domain.usecase.schedule.GetUpdatedScheduleHistoryUseCase
import com.khomenok.monitorschedule.domain.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ScheduleUpdatedHistoryViewModel(
    private val getUpdatedScheduleHistoryUseCase: GetUpdatedScheduleHistoryUseCase
): ViewModel() {

    private val actionsList = MutableLiveData<ArrayList<ScheduleUpdatedAction>>()
    private val error = MutableLiveData<StateStatus>(null)
    val actions = actionsList
    val errorStatus = error

    fun closeError() {
        error.value = null
    }

    fun getActions(schedule: Schedule) {
        viewModelScope.launch(Dispatchers.IO) {
            val actionsResult = getUpdatedScheduleHistoryUseCase.execute(schedule)
            if (actionsResult is Resource.Success && actionsResult.data != null) {
                actionsList.postValue(actionsResult.data)
            } else {
                errorStatus.postValue(
                    StateStatus(
                        StateStatus.ERROR_STATE,
                        actionsResult.statusCode
                    )
                )
            }
        }
    }

}