package com.khomenok.monitorschedule.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.khomenok.monitorschedule.presentation.widgets.WakeLocker
import com.khomenok.monitorschedule.receiver.ScheduleUpdater

class ScheduleUpdateService : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        WakeLocker.acquire(context)

        val widgetIntent = Intent(context, ScheduleUpdater::class.java)
        widgetIntent.action = "com.khomenok.monitorschedule.action.scheduleUpdater"
        context.sendBroadcast(widgetIntent)

        WakeLocker.release()
    }

}