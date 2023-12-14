package com.khomenok.monitorschedule.data.db.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.khomenok.monitorschedule.domain.models.ScheduleSubject
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class ScheduleSubjectsListConverter {

    @TypeConverter
    fun fromSubjectsToString(subjects: ArrayList<ScheduleSubject>?): String? {
        return Gson().toJson(subjects)
    }

    @TypeConverter
    fun fromStringToGroup(subjects: String): ArrayList<ScheduleSubject>? {
        val listType = object: TypeToken<ArrayList<ScheduleSubject>>(){}.type
        return Gson().fromJson(subjects, listType)
    }
}