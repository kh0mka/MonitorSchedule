package com.khomenok.monitorschedule.domain.usecase

import com.khomenok.monitorschedule.domain.models.SavedSchedule
import com.khomenok.monitorschedule.domain.repository.SavedScheduleRepository
import com.khomenok.monitorschedule.domain.utils.Resource
import com.khomenok.monitorschedule.domain.utils.StatusCode
import kotlin.collections.ArrayList

class GetSavedScheduleUseCase(private val savedScheduleRepository: SavedScheduleRepository) {

    suspend fun getSavedSchedules(): Resource<ArrayList<SavedSchedule>> {
        val result = savedScheduleRepository.getSavedSchedules()
        return try {
            when(result) {
                is Resource.Success -> {
                    Resource.Success(result.data!!)
                }
                is Resource.Error -> {
                    Resource.Error(
                        statusCode = result.statusCode,
                        message = result.message
                    )
                }
            }
        } catch (e: Exception) {
            Resource.Error(
                statusCode = StatusCode.DATA_ERROR,
                message = e.message
            )
        }
    }

    suspend fun saveSchedule(schedule: SavedSchedule): Resource<Unit> {
        return savedScheduleRepository.saveSchedule(schedule)
    }

    suspend fun saveSchedulesList(schedulesList: ArrayList<SavedSchedule>): Resource<Unit> {
        return savedScheduleRepository.saveSchedulesList(schedulesList)
    }

    suspend fun filterByKeywordASC(title: String): Resource<ArrayList<SavedSchedule>> {
        val result = savedScheduleRepository.filterByKeywordASC(title)
        return try {
            when(result) {
                is Resource.Success -> {
                    Resource.Success(result.data!!)
                }
                is Resource.Error -> {
                    Resource.Error(
                        statusCode = result.statusCode,
                        message = result.message
                    )
                }
            }
        } catch (e: Exception) {
            Resource.Error(
                statusCode = StatusCode.DATA_ERROR,
                message = e.message
            )
        }
    }

    suspend fun filterByKeywordDESC(title: String): Resource<ArrayList<SavedSchedule>> {
        val result = savedScheduleRepository.filterByKeywordDESC(title)
        return try {
            when(result) {
                is Resource.Success -> {
                    Resource.Success(result.data!!)
                }
                is Resource.Error -> {
                    Resource.Error(
                        statusCode = result.statusCode,
                        message = result.message
                    )
                }
            }
        } catch (e: Exception) {
            Resource.Error(
                statusCode = StatusCode.DATA_ERROR,
                message = e.message
            )
        }
    }

    suspend fun deleteSchedule(schedule: SavedSchedule): Resource<Unit> {
        return savedScheduleRepository.deleteSchedule(schedule)
    }

}