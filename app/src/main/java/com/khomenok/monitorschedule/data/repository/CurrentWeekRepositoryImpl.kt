package com.khomenok.monitorschedule.data.repository

import com.khomenok.monitorschedule.api.RetrofitBuilder
import com.khomenok.monitorschedule.api.services.GetCurrentWeekService
import com.khomenok.monitorschedule.data.db.dao.CurrentWeekDao
import com.khomenok.monitorschedule.domain.models.CurrentWeek
import com.khomenok.monitorschedule.domain.repository.CurrentWeekRepository
import com.khomenok.monitorschedule.domain.utils.Resource
import com.khomenok.monitorschedule.domain.utils.StatusCode
import java.util.*

class CurrentWeekRepositoryImpl(private val currentWeekDao: CurrentWeekDao) : CurrentWeekRepository {

    override suspend fun getCurrentWeekAPI(): Resource<CurrentWeek> {
        val currentWeekService = RetrofitBuilder.getInstance().create(GetCurrentWeekService::class.java)

        return try {
            val result = currentWeekService.getCurrentWeek()
            val data = result.body()
            return if (result.isSuccessful && data != null) {
                Resource.Success(CurrentWeek(week = data, time = Date().time))
            } else {
                Resource.Error(
                    statusCode = StatusCode.SERVER_ERROR,
                    message = result.message()
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(
                statusCode = StatusCode.CONNECTION_ERROR,
                message = e.message
            )
        }
    }

    override suspend fun getCurrentWeek(): Resource<CurrentWeek> {

        return try {
            val data = currentWeekDao.getCurrentWeek()
                ?: return Resource.Error(
                    statusCode = StatusCode.DATABASE_NOT_FOUND_ERROR
                )
            Resource.Success(data.toCurrentWeek())
        } catch (e: Exception) {
            Resource.Error(
                statusCode = StatusCode.DATABASE_ERROR,
                message = e.message
            )
        }
    }

    override suspend fun saveCurrentWeek(currentWeek: CurrentWeek): Resource<Unit> {
        return try {
            currentWeekDao.saveCurrentWeek(currentWeek.toCurrentWeekTable())
            Resource.Success(null)
        } catch (e: Exception) {
            Resource.Error(
                statusCode = StatusCode.DATABASE_ERROR,
                message = e.message
            )
        }
    }
}