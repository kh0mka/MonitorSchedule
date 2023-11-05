package com.khomenok.monitorschedule.data.repository

import com.khomenok.monitorschedule.api.RetrofitBuilder
import com.khomenok.monitorschedule.api.services.GetDepartmentsService
import com.khomenok.monitorschedule.data.db.dao.DepartmentDao
import com.khomenok.monitorschedule.domain.models.Department
import com.khomenok.monitorschedule.domain.repository.DepartmentRepository
import com.khomenok.monitorschedule.domain.utils.Resource
import com.khomenok.monitorschedule.domain.utils.StatusCode

class DepartmentRepositoryImpl(override val departmentDao: DepartmentDao) : DepartmentRepository {

    override suspend fun getDepartmentsAPI(): Resource<ArrayList<Department>> {
        val departmentsService = RetrofitBuilder.getInstance().create(GetDepartmentsService::class.java)

        return try {
            val result = departmentsService.getDepartments()
            val data = result.body()
            return if (result.isSuccessful && data != null) {
                Resource.Success(data)
            } else {
                Resource.Error(
                    statusCode = StatusCode.SERVER_ERROR,
                    message = result.message()
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(
                statusCode = StatusCode.CONNECTION_ERROR,
                message = e.message
            )
        }
    }

    override suspend fun getDepartments(): Resource<ArrayList<Department>> {

        return try {
            val result = departmentDao.getDepartments()
            val departmentsList = result.map { it.toDepartment() } as ArrayList<Department>
            Resource.Success(departmentsList)
        } catch (e: Exception) {
            Resource.Error(
                statusCode = StatusCode.DATABASE_ERROR,
                message = e.message,
            )
        }
    }

    override suspend fun getDepartmentByAbbrev(abbrev: String): Resource<Department> {

        return try {
            val result = departmentDao.getDepartmentByAbbr(abbrev)
            Resource.Success(result.toDepartment())
        } catch (e: Exception) {
            Resource.Error(
                statusCode = StatusCode.DATABASE_ERROR,
                message = e.message,
            )
        }
    }

    override suspend fun saveDepartments(departmentList: ArrayList<Department>): Resource<Unit> {

        return try {
            val departmentsList = departmentList.map { it.toDepartmentTable() }
            departmentDao.saveDepartments(departmentsList)
            Resource.Success(null)
        } catch (e: Exception) {
            Resource.Error(
                statusCode = StatusCode.DATABASE_ERROR,
                message = e.message,
            )
        }
    }

}