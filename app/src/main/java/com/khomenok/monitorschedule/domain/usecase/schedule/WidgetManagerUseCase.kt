package com.khomenok.monitorschedule.domain.usecase.schedule

import com.khomenok.monitorschedule.domain.models.WidgetSettings
import com.khomenok.monitorschedule.domain.repository.WidgetSettingsRepository

class WidgetManagerUseCase (
    private val widgetSettingsRepository: WidgetSettingsRepository
) {

    fun getWidgetSettings(widgetId: Int): WidgetSettings? {
        return widgetSettingsRepository.getWidgetSettings(widgetId)
    }

    fun saveWidgetSettings(widgetSettings: WidgetSettings) {
        widgetSettingsRepository.saveWidgetSettings(widgetSettings)
    }

    fun deleteWidgetSettings(widgetId: Int) {
        widgetSettingsRepository.deleteWidgetSettings(widgetId)
    }

}