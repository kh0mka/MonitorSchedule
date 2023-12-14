package com.khomenok.monitorschedule.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.work.*
import com.khomenok.monitorschedule.data.repository.SharedPrefsRepositoryImpl
import com.khomenok.monitorschedule.data.repository.ThemeType
import com.khomenok.monitorschedule.presentation.di.appModule
import com.khomenok.monitorschedule.presentation.di.dataModule
import com.khomenok.monitorschedule.presentation.di.domainModule
import com.khomenok.monitorschedule.worker.ScheduleUpdateWorker
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import java.text.SimpleDateFormat
import java.time.Duration
import java.util.*
import java.util.concurrent.TimeUnit

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        val prefs = SharedPrefsRepositoryImpl(this)

        // Detect from data/repositorySharedPrefsrepositoryImpl theme of system or another and
        // sets it with helps of AppCompat library
        when (prefs.getThemeType()) {
            ThemeType.SYSTEM -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            ThemeType.DARK -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            ThemeType.LIGHT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        val todayDateFormat = SimpleDateFormat("dd.MM.yyyy")
        val todayDate = todayDateFormat.format(Date().time)
        val scheduleAutoUpdateDate = prefs.getScheduleAutoUpdateDate()
        if (scheduleAutoUpdateDate != "" && prefs.getScheduleAutoUpdateDate() != todayDate) {
            initScheduleUpdated()
            prefs.setScheduleAutoUpdateDate(todayDate)
        }

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(listOf(appModule, dataModule, domainModule))
        }
    }

    // Initialize background task to update schedule every day in 20:00 PM
    private fun initScheduleUpdated() {
        val calendar = Calendar.getInstance(Locale("ru", "BY"))
        calendar.set(Calendar.HOUR, 20)
        calendar.set(Calendar.AM_PM, Calendar.AM)
        calendar.set(Calendar.MINUTE, 0)
        val updateRequest = PeriodicWorkRequestBuilder<ScheduleUpdateWorker>(
            Duration.ofDays(1)
        )
            .setInitialDelay(calendar.timeInMillis - Date().time, TimeUnit.MILLISECONDS)
            .setConstraints( // Set constrains (requirements) of WorkRequest to work with this
                Constraints.Builder()
                    .setRequiredNetworkType(
                        NetworkType.CONNECTED // requirements of schedule updates
                    )
                    .build()
            )
            .build()
        val workManager = WorkManager.getInstance(applicationContext)
        workManager.enqueueUniquePeriodicWork(
            "update",
            ExistingPeriodicWorkPolicy.REPLACE,
            updateRequest
        )
    }

}