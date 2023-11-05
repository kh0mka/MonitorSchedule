package com.khomenok.monitorschedule.domain.usecase.schedule

import com.khomenok.monitorschedule.domain.models.Schedule
import com.khomenok.monitorschedule.domain.repository.ScheduleRepository
import com.khomenok.monitorschedule.domain.utils.Resource

class SaveScheduleUseCase(
    private val scheduleRepository: ScheduleRepository,
) {

    suspend fun execute(schedule: Schedule): Resource<Unit> {
        return scheduleRepository.saveSchedule(schedule)
    }

}