package com.khomenok.monitorschedule.domain.usecase

import com.khomenok.monitorschedule.domain.models.CurrentWeek
import com.khomenok.monitorschedule.domain.repository.CurrentWeekRepository
import com.khomenok.monitorschedule.domain.utils.Resource
import com.khomenok.monitorschedule.domain.utils.StatusCode
import com.khomenok.monitorschedule.domain.utils.WeekManager

class GetCurrentWeekUseCase(private val currentWeekRepository: CurrentWeekRepository) {

    suspend fun getCurrentWeekAPI(): Resource<CurrentWeek> {
        return currentWeekRepository.getCurrentWeekAPI()
    }

    suspend fun updateWeekNumber(): Resource<CurrentWeek> {
        val currentWeekNum = getCurrentWeekAPI()

        if (currentWeekNum is Resource.Success && currentWeekNum.data != null) {
            saveCurrentWeek(currentWeekNum.data)
        }

        return currentWeekNum
    }

    suspend fun isCurrentWeekPassed(): Resource<Boolean> {
        val initWeek = currentWeekRepository.getCurrentWeek()
        val weekManager = WeekManager()

        return if (initWeek is Resource.Success && initWeek.data != null) {
            Resource.Success(weekManager.isWeekPassed(initWeek.data))
        } else {
            Resource.Error(
                statusCode = initWeek.statusCode,
                message = initWeek.message
            )
        }
    }

    suspend fun getCurrentWeek(): Resource<Int> {
        val initWeek = currentWeekRepository.getCurrentWeek()
        val weekManager = WeekManager()

        return try {
            if (initWeek is Resource.Success && initWeek.data != null) {
                val gotWeek = weekManager.getCurrentWeek(initWeek.data)
                Resource.Success(gotWeek)
            } else {
                Resource.Error(
                    statusCode = initWeek.statusCode,
                    message = initWeek.message
                )
            }
        } catch (e: Exception) {
            Resource.Error(
                statusCode = StatusCode.DATA_ERROR,
                message = e.message
            )
        }
    }

    suspend fun saveCurrentWeek(currentWeek: CurrentWeek): Resource<Unit> {
        return currentWeekRepository.saveCurrentWeek(currentWeek)
    }

}