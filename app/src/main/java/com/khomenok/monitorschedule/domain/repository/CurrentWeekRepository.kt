package com.khomenok.monitorschedule.domain.repository

import com.khomenok.monitorschedule.domain.models.CurrentWeek
import com.khomenok.monitorschedule.domain.utils.Resource

interface CurrentWeekRepository {

    suspend fun getCurrentWeekAPI(): Resource<CurrentWeek>

    suspend fun getCurrentWeek(): Resource<CurrentWeek>

    suspend fun saveCurrentWeek(currentWeek: CurrentWeek): Resource<Unit>

}