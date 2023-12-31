package com.khomenok.monitorschedule.domain.usecase.schedule

import com.khomenok.monitorschedule.domain.models.*
import com.khomenok.monitorschedule.domain.repository.EmployeeItemsRepository
import com.khomenok.monitorschedule.domain.repository.GroupItemsRepository
import com.khomenok.monitorschedule.domain.repository.ScheduleRepository
import com.khomenok.monitorschedule.domain.usecase.GetCurrentWeekUseCase
import com.khomenok.monitorschedule.domain.utils.Resource
import com.khomenok.monitorschedule.domain.utils.ScheduleController
import com.khomenok.monitorschedule.domain.utils.StatusCode
import java.util.*
import kotlin.collections.ArrayList

class GetScheduleUseCase(
    private val scheduleRepository: ScheduleRepository,
    private val groupItemsRepository: GroupItemsRepository,
    private val employeeItemsRepository: EmployeeItemsRepository,
    private val currentWeekUseCase: GetCurrentWeekUseCase
) {

    suspend fun getGroupAPI(groupName: String): Resource<Schedule> {

        return try {
            when (
                val apiSchedule = scheduleRepository.getGroupScheduleAPI(groupName)
            ) {
                is Resource.Success -> {
                    val data = apiSchedule.data!!
                    val currentWeek = currentWeekUseCase.getCurrentWeek()
                    if (currentWeek is Resource.Error) {
                        return Resource.Error(
                            statusCode = currentWeek.statusCode,
                            message = currentWeek.message
                        )
                    }
                    val schedule = getNormalSchedule(data, currentWeek.data!!)
                    setActualSettings(schedule)
                    val isMergedFacultyAndSpeciality = mergeSpecialitiesAndFaculties(schedule)
                    if (isMergedFacultyAndSpeciality is Resource.Error) {
                        return isMergedFacultyAndSpeciality
                    }
                    val isMergedEmployees = mergeEmployeeItems(schedule)
                    if (isMergedEmployees is Resource.Error) {
                        return isMergedEmployees
                    }
                    setPrevOriginalSchedule(schedule)
                    Resource.Success(schedule)
                }
                is Resource.Error -> {
                    Resource.Error(
                        statusCode = apiSchedule.statusCode,
                        message = apiSchedule.message
                    )
                }
            }
        } catch (e: Exception) {
            return Resource.Error(
                statusCode = StatusCode.DATA_ERROR,
                message = e.message
            )
        }
    }

    suspend fun getEmployeeAPI(urlId: String): Resource<Schedule> {

        return try {
            when (
                val apiSchedule = scheduleRepository.getEmployeeScheduleAPI(urlId)
            ) {
                is Resource.Success -> {
                    val data = apiSchedule.data!!
                    when (
                        val groupItems = groupItemsRepository.getAllGroupItems()
                    ) {
                        is Resource.Success -> {
                            val currentWeek = currentWeekUseCase.getCurrentWeek()
                            if (currentWeek is Resource.Error) {
                                return Resource.Error(
                                    statusCode = currentWeek.statusCode,
                                    message = currentWeek.message
                                )
                            }
                            val schedule = getNormalSchedule(data, currentWeek.data!!)
                            setActualSettings(schedule)
                            mergeGroupsSubjects(schedule, groupItems.data!!)
                            val isMergedDepartments = mergeDepartments(schedule)
                            if (isMergedDepartments is Resource.Error) {
                                return isMergedDepartments
                            }
                            setPrevOriginalSchedule(schedule)
                            return Resource.Success(schedule)
                        }
                        is Resource.Error -> {
                            return Resource.Error(
                                statusCode = groupItems.statusCode,
                                message = groupItems.message
                            )
                        }
                    }
                }
                is Resource.Error -> {
                    return Resource.Error(
                        statusCode = apiSchedule.statusCode,
                        message = apiSchedule.message
                    )
                }
            }
        } catch(e: Exception) {
            e.printStackTrace()
            Resource.Error(
                statusCode = StatusCode.DATA_ERROR,
                message = e.message
            )
        }
    }

    private fun mergeGroupsSubjects(schedule: Schedule, groupItems: ArrayList<Group>) {
        val scheduleController = ScheduleController()
        scheduleController.mergeGroupsSubjects(schedule, groupItems)
    }

    private suspend fun mergeDepartments(schedule: Schedule): Resource<Schedule> {
        return when (
            val result = employeeItemsRepository.getEmployeeItems()
        ) {
            is Resource.Success -> {
                val data = result.data!!
                if (schedule.employee.id == -1) {
                    return Resource.Error(
                        statusCode = StatusCode.DATA_ERROR
                    )
                }
                val employeeMatch = data.find { it.id == schedule.employee.id }
                schedule.employee.departmentsList = employeeMatch?.departments
                Resource.Success(null)
            }
            is Resource.Error -> {
                Resource.Error(
                    statusCode = result.statusCode,
                    message = result.message
                )
            }
        }
    }

    private suspend fun mergeEmployeeItems(schedule: Schedule): Resource<Schedule> {
        return when (
            val result = employeeItemsRepository.getEmployeeItems()
        ) {
            is Resource.Success -> {
                val data = result.data!!
                schedule.schedules.map { day ->
                    day.schedule.map { subject ->
                        val employeeList = ArrayList<EmployeeSubject>()
                        subject.employees?.forEach { employeeItem ->
                            val employee = data.find { it.id == employeeItem.id }
                            if (employee != null) {
                                employeeList.add(employee.toEmployeeSubject())
                            } else {
                                employeeList.add(employeeItem)
                            }
                        }
                        subject.employees = employeeList
                    }
                }
                Resource.Success(schedule)
            }
            is Resource.Error -> {
                Resource.Error(
                    statusCode = result.statusCode,
                    message = result.message
                )
            }
        }
    }

    private suspend fun mergeSpecialitiesAndFaculties(schedule: Schedule): Resource<Schedule> {
        return when (
            val result = groupItemsRepository.getAllGroupItems()
        ) {
            is Resource.Success -> {
                val data = result.data!!
                if (schedule.group.id == -1) {
                    return Resource.Error(
                        statusCode = StatusCode.DATA_ERROR
                    )
                }
                val groupMatch = data.find { it.id == schedule.group.id }
                schedule.group.speciality = groupMatch?.speciality
                schedule.group.faculty = groupMatch?.faculty
                Resource.Success(schedule)
            }
            is Resource.Error -> {
                Resource.Error(
                    statusCode = result.statusCode,
                    message = result.message
                )
            }
        }
    }

    private fun getNormalSchedule(groupSchedule: GroupSchedule, currentWeekNumber: Int): Schedule {
        val scheduleController = ScheduleController()
        val originalSchedule = scheduleController.getOriginalSchedule(groupSchedule)

        val normalSchedule = scheduleController.getBasicSchedule(groupSchedule, currentWeekNumber)
        normalSchedule.originalSchedule = originalSchedule

        return normalSchedule
    }

    private suspend fun setPrevOriginalSchedule(schedule: Schedule) {
        val oldSchedule = scheduleRepository.getScheduleById(schedule.id)

        if (oldSchedule is Resource.Success && oldSchedule.data != null) {
            if (oldSchedule.data.originalSchedule != schedule.originalSchedule) {
                schedule.prevOriginalSchedule = oldSchedule.data.originalSchedule
                schedule.lastUpdateTime = Date().time
            }
        }
    }

    private suspend fun setActualSettings(schedule: Schedule) {
        val foundSchedule = getById(schedule.id)
        if (foundSchedule is Resource.Success) {
            schedule.settings = foundSchedule.data!!.settings
        }
    }

    suspend fun getById(groupId: Int, ignoreSettings: Boolean = false): Resource<Schedule> {
        return try {
            when (
                val result = scheduleRepository.getScheduleById(groupId)
            ) {
                is Resource.Success -> {
                    val data = result.data!!
                    val scheduleController = ScheduleController()
                    val schedule = scheduleController.getRegularSchedule(data, ignoreSettings)
                    Resource.Success(schedule)
                }
                is Resource.Error -> {
                    Resource.Error(
                        statusCode = result.statusCode,
                        message = result.message
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(
                statusCode = StatusCode.DATA_ERROR,
                message = e.message
            )
        }
    }

}