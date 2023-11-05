package com.khomenok.monitorschedule.domain.repository

import com.khomenok.monitorschedule.data.db.dao.DepartmentDao
import com.khomenok.monitorschedule.domain.models.Department
import com.khomenok.monitorschedule.domain.utils.Resource

interface DepartmentRepository {

    val departmentDao: DepartmentDao

    suspend fun getDepartmentsAPI(): Resource<ArrayList<Department>>

    suspend fun getDepartments(): Resource<ArrayList<Department>>

    suspend fun getDepartmentByAbbrev(abbrev: String): Resource<Department>

    suspend fun saveDepartments(departmentList: ArrayList<Department>): Resource<Unit>

}