package com.khomenok.monitorschedule.domain.repository

import com.khomenok.monitorschedule.data.db.dao.EmployeeDao
import com.khomenok.monitorschedule.domain.models.Employee
import com.khomenok.monitorschedule.domain.utils.Resource

interface EmployeeItemsRepository {

    val employeeDao: EmployeeDao

    suspend fun getEmployeeItemsAPI(): Resource<ArrayList<Employee>>

    suspend fun getEmployeeItems(): Resource<ArrayList<Employee>>

    suspend fun filterByKeywordASC(s: String): Resource<ArrayList<Employee>>

    suspend fun filterByKeywordDESC(s: String): Resource<ArrayList<Employee>>

    suspend fun saveEmployeeItem(employeeList: ArrayList<Employee>): Resource<Unit>

}