package com.khomenok.monitorschedule.domain.models

import com.google.gson.annotations.SerializedName

data class GroupScheduleWeek (
    @SerializedName("Понедельник") var monday: ArrayList<ScheduleSubject>? = ArrayList(),
    @SerializedName("Вторник") var tuesday: ArrayList<ScheduleSubject>? = ArrayList(),
    @SerializedName("Среда") var wednesday: ArrayList<ScheduleSubject>? = ArrayList(),
    @SerializedName("Четверг") var thursday: ArrayList<ScheduleSubject>? = ArrayList(),
    @SerializedName("Пятница") var friday: ArrayList<ScheduleSubject>? = ArrayList(),
    @SerializedName("Суббота") var saturday: ArrayList<ScheduleSubject>? = ArrayList(),
    @SerializedName("Воскресенье") var sunday: ArrayList<ScheduleSubject>? = ArrayList(),
) {

    companion object {
        val empty = GroupScheduleWeek(
            monday = ArrayList(),
            tuesday = ArrayList(),
            wednesday = ArrayList(),
            thursday = ArrayList(),
            friday = ArrayList(),
            saturday = ArrayList(),
        )
    }

}