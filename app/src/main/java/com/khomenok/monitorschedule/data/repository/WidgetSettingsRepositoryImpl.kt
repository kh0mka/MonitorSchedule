package com.khomenok.monitorschedule.data.repository

import com.khomenok.monitorschedule.data.db.dao.WidgetSettingsDao
import com.khomenok.monitorschedule.domain.models.WidgetSettings
import com.khomenok.monitorschedule.domain.repository.WidgetSettingsRepository

class WidgetSettingsRepositoryImpl(
    override val widgetSettingsDao: WidgetSettingsDao
) : WidgetSettingsRepository {

    override fun getWidgetSettings(widgetId: Int): WidgetSettings? {
        val widgetSettingTable = widgetSettingsDao.getWidgetSettings(widgetId)

        return widgetSettingTable?.toWidgetSettings()
    }

    override fun saveWidgetSettings(widgetSettings: WidgetSettings) {
        widgetSettingsDao.saveWidgetSettings(widgetSettings.toWidgetSettingsTable())
    }

    override fun deleteWidgetSettings(widgetId: Int) {
        widgetSettingsDao.deleteWidgetSettings(widgetId)
    }

}