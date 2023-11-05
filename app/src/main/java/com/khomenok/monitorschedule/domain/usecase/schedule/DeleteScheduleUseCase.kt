package com.khomenok.monitorschedule.domain.usecase.schedule

import com.khomenok.monitorschedule.domain.models.SavedSchedule
import com.khomenok.monitorschedule.domain.repository.ScheduleRepository
import com.khomenok.monitorschedule.domain.utils.Resource

class DeleteScheduleUseCase(
    private val scheduleRepository: ScheduleRepository,
) {

    suspend fun invoke(savedSchedule: SavedSchedule): Resource<Unit> {
        return scheduleRepository.deleteScheduleById(savedSchedule.id)
    }

}