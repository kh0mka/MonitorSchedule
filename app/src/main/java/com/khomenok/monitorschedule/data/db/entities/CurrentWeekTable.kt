package com.khomenok.monitorschedule.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.khomenok.monitorschedule.domain.models.CurrentWeek

@Entity
data class CurrentWeekTable(
    @PrimaryKey val id: Int = 0,
    @ColumnInfo val week: Int,
    @ColumnInfo val time: Long
) {

    fun toCurrentWeek() = CurrentWeek(
        week = week,
        time = time
    )

}