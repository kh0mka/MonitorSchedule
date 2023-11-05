package com.khomenok.monitorschedule.domain.repository

import com.khomenok.monitorschedule.data.db.dao.WidgetSettingsDao
import com.khomenok.monitorschedule.domain.models.WidgetSettings

interface WidgetSettingsRepository {

    val widgetSettingsDao: WidgetSettingsDao

    fun getWidgetSettings(widgetId: Int): WidgetSettings?

    fun saveWidgetSettings(widgetSettings: WidgetSettings)

    fun deleteWidgetSettings(widgetId: Int)

}