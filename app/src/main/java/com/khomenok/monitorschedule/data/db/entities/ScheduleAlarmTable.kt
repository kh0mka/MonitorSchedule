package com.khomenok.monitorschedule.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.khomenok.monitorschedule.domain.models.scheduleSettings.ScheduleSettingsAlarm

@Entity
data class ScheduleAlarmTable (
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo val createDate: Long = 0L,
    @ColumnInfo val isTurnOn: Boolean,
    @ColumnInfo val wakeUpTime: Long,
    @ColumnInfo val weekNumbers: List<Int>,
    @ColumnInfo val weekDayNumbers: List<Int>,
    @ColumnInfo val isOneTime: Boolean,
    @ColumnInfo val music: String
) {

    fun toScheduleAlarm() = ScheduleSettingsAlarm(
        id = id,
        createDate = createDate,
        isTurnOn = isTurnOn,
        wakeUpTime = wakeUpTime,
        weekNumbers = weekNumbers,
        weekDayNumbers = weekDayNumbers,
        isOneTime = isOneTime,
        music = music
    )

}