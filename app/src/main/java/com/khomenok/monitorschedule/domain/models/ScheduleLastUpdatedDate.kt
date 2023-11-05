package com.khomenok.monitorschedule.domain.models

data class ScheduleLastUpdatedDate (
    val lastUpdateDate: String?
) {

    companion object {
        val empty = ScheduleLastUpdatedDate(
            lastUpdateDate = ""
        )
    }

}