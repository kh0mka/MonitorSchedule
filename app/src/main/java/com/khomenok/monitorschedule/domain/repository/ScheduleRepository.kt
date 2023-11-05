package com.khomenok.monitorschedule.domain.repository

import com.khomenok.monitorschedule.data.db.dao.ScheduleDao
import com.khomenok.monitorschedule.domain.models.GroupSchedule
import com.khomenok.monitorschedule.domain.models.Schedule
import com.khomenok.monitorschedule.domain.models.ScheduleLastUpdatedDate
import com.khomenok.monitorschedule.domain.models.scheduleSettings.ScheduleSettings
import com.khomenok.monitorschedule.domain.utils.Resource

interface ScheduleRepository {

    val scheduleDao: ScheduleDao

    suspend fun getGroupScheduleAPI(groupName: String): Resource<GroupSchedule>

    suspend fun getEmployeeScheduleAPI(groupName: String): Resource<GroupSchedule>

    suspend fun getEmployeeLastUpdatedDate(scheduleId: Int): Resource<ScheduleLastUpdatedDate>

    suspend fun getGroupLastUpdatedDate(scheduleId: Int): Resource<ScheduleLastUpdatedDate>

    suspend fun getScheduleById(id: Int): Resource<Schedule>

    suspend fun saveSchedule(schedule: Schedule): Resource<Unit>

    suspend fun updateScheduleSettings(id: Int, newSettings: ScheduleSettings): Resource<Unit>

    suspend fun deleteScheduleById(scheduleId: Int): Resource<Unit>

}