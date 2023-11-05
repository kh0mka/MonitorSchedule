package com.khomenok.monitorschedule.domain.usecase.schedule

import com.khomenok.monitorschedule.domain.models.scheduleSettings.ScheduleSettings
import com.khomenok.monitorschedule.domain.repository.ScheduleRepository
import com.khomenok.monitorschedule.domain.utils.Resource

class UpdateScheduleSettingsUseCase(
    private val scheduleRepository: ScheduleRepository,
) {

    suspend fun invoke(id: Int, newSchedule: ScheduleSettings): Resource<Unit> {
        return scheduleRepository.updateScheduleSettings(id, newSchedule)
    }

}