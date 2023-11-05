package com.khomenok.monitorschedule.presentation.widgets

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import com.khomenok.monitorschedule.R

/**
 * Implementation of App Widget functionality.
 */
class MainWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        val views = RemoteViews(context.packageName, R.layout.main_widget)
        for (appWidgetId in appWidgetIds) {
            val intent = Intent(context, MainWidgetService::class.java)
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))
            views.setRemoteAdapter(appWidgetId, R.id.itemsListView, intent)
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Function implementation for creating the first widget
    }

    override fun onDisabled(context: Context) {
        // Function implementation for disable the last widget
    }
}


