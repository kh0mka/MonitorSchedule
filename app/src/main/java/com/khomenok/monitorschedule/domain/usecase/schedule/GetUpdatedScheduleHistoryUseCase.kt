package com.khomenok.monitorschedule.domain.usecase.schedule

import com.khomenok.monitorschedule.domain.models.Schedule
import com.khomenok.monitorschedule.domain.models.ScheduleDay
import com.khomenok.monitorschedule.domain.models.ScheduleSubject
import com.khomenok.monitorschedule.domain.models.ScheduleUpdatedAction
import com.khomenok.monitorschedule.domain.utils.Resource
import com.khomenok.monitorschedule.domain.utils.ScheduleUpdatedController
import com.khomenok.monitorschedule.domain.utils.StatusCode

class GetUpdatedScheduleHistoryUseCase {

    fun execute(schedule: Schedule): Resource<ArrayList<ScheduleUpdatedAction>> {
        return try {
            val prevSchedule = schedule.originalSchedule.map { it.copy() } as ArrayList<ScheduleDay>
            prevSchedule.map { day ->
                day.schedule = day.schedule.map { it.copy() } as ArrayList<ScheduleSubject>
            }
            prevSchedule[0].schedule[0].note = "Hello World"
            prevSchedule[0].schedule[1].lessonTypeAbbrev = ScheduleSubject.LESSON_TYPE_EXAM

            val scheduleUpdateController = ScheduleUpdatedController(
                prevOriginalSchedule = schedule.originalSchedule,
                currOriginalSchedule = prevSchedule
            )
            Resource.Success(scheduleUpdateController.getChangedActions())
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(
                statusCode = StatusCode.DATA_ERROR
            )
        }
    }

}