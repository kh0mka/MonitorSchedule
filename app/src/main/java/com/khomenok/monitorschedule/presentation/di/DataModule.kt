package com.khomenok.monitorschedule.presentation.di

import android.app.Application
import androidx.room.Room
import com.khomenok.monitorschedule.data.db.AppDatabase
import com.khomenok.monitorschedule.data.db.converters.*
import com.khomenok.monitorschedule.data.repository.*
import com.khomenok.monitorschedule.domain.repository.*
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dataModule = module {

    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            "AppDB"
        )
            .addTypeConverter(DepartmentConverter())
            .addTypeConverter(IntListConverter())
            .addTypeConverter(StrListConverter())
            .addTypeConverter(ScheduleSettingsConverter())
            .addTypeConverter(ScheduleDayListConverter())
            .addTypeConverter(ScheduleSubjectsListConverter())
            .build()
    }

    fun groupDao(db: AppDatabase) = db.groupDao()

    fun specialityDao(db: AppDatabase) = db.specialityDao()

    fun facultyDao(db: AppDatabase) = db.facultyDao()

    fun departmentDao(db: AppDatabase) = db.departmentDao()

    fun scheduleDao(db: AppDatabase) = db.scheduleDao()

    fun employeeDao(db: AppDatabase) = db.employeeDao()

    fun savedScheduleDao(db: AppDatabase) = db.savedScheduleDao()

    fun currentWeekDao(db: AppDatabase) = db.currentWeekDao()

    fun widgetSettingsDao(db: AppDatabase) = db.widgetSettingsDao()

    single { groupDao(get()) }

    single { specialityDao(get()) }

    single { facultyDao(get()) }

    single { departmentDao(get()) }

    single { scheduleDao(get()) }

    single { employeeDao(get()) }

    single { savedScheduleDao(get()) }

    single { currentWeekDao(get()) }

    single { widgetSettingsDao(get()) }

    single {
        provideDatabase(androidApplication())
    }

    single<SharedPrefsRepository> {
        SharedPrefsRepositoryImpl(get())
    }

    single<CurrentWeekRepository> {
        CurrentWeekRepositoryImpl(get())
    }

    single<ScheduleRepository> {
        ScheduleRepositoryImpl(get())
    }

    single<GroupItemsRepository> {
        GroupItemsRepositoryImpl(get())
    }

    single<SavedScheduleRepository> {
        SavedScheduleRepositoryImpl(get())
    }

    single<EmployeeItemsRepository> {
        EmployeeItemsRepositoryImpl(get())
    }

    single<FacultyRepository> {
        FacultyRepositoryImpl(get())
    }

    single<SpecialityRepository> {
        SpecialityRepositoryImpl(get())
    }

    single<WidgetSettingsRepository> {
        WidgetSettingsRepositoryImpl(get())
    }

    single<DepartmentRepository> {
        DepartmentRepositoryImpl(get())
    }

}