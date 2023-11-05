package com.khomenok.monitorschedule.domain.models

import com.khomenok.monitorschedule.data.db.entities.CurrentWeekTable

data class CurrentWeek(
    val week: Int,
    val time: Long
) {

    fun toCurrentWeekTable() = CurrentWeekTable(
        week = week,
        time = time
    )

}