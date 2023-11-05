package com.khomenok.monitorschedule.domain.usecase

import com.khomenok.monitorschedule.domain.models.ScheduleLastUpdatedDate
import com.khomenok.monitorschedule.domain.repository.ScheduleRepository
import com.khomenok.monitorschedule.domain.utils.Resource

class GetScheduleLastUpdateUseCase(
    private val scheduleRepository: ScheduleRepository
) {

    suspend fun getGroupLastUpdateDateByID(scheduleId: Int): Resource<ScheduleLastUpdatedDate> {
        return scheduleRepository.getGroupLastUpdatedDate(scheduleId)
    }

    suspend fun getEmployeeLastUpdateDateByID(scheduleId: Int): Resource<ScheduleLastUpdatedDate> {
        return scheduleRepository.getEmployeeLastUpdatedDate(scheduleId)
    }

}