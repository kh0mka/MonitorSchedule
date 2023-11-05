package com.khomenok.monitorschedule.domain.repository

import com.khomenok.monitorschedule.data.db.dao.SavedScheduleDao
import com.khomenok.monitorschedule.domain.models.SavedSchedule
import com.khomenok.monitorschedule.domain.utils.Resource

interface SavedScheduleRepository {

    val savedScheduleDao: SavedScheduleDao

    suspend fun getSavedSchedules(): Resource<ArrayList<SavedSchedule>>

    suspend fun saveSchedule(schedule: SavedSchedule): Resource<Unit>

    suspend fun saveSchedulesList(schedulesList: ArrayList<SavedSchedule>): Resource<Unit>

    suspend fun getSavedScheduleById(scheduleId: Int): Resource<SavedSchedule>

    suspend fun filterByKeywordASC(title: String): Resource<ArrayList<SavedSchedule>>

    suspend fun filterByKeywordDESC(title: String): Resource<ArrayList<SavedSchedule>>

    suspend fun deleteSchedule(schedule: SavedSchedule): Resource<Unit>

}