package com.khomenok.monitorschedule.data.repository

import com.khomenok.monitorschedule.api.RetrofitBuilder
import com.khomenok.monitorschedule.api.services.GetEmployeeItemsService
import com.khomenok.monitorschedule.data.db.dao.EmployeeDao
import com.khomenok.monitorschedule.domain.models.Employee
import com.khomenok.monitorschedule.domain.repository.EmployeeItemsRepository
import com.khomenok.monitorschedule.domain.utils.Resource
import com.khomenok.monitorschedule.domain.utils.StatusCode

class EmployeeItemsRepositoryImpl(override val employeeDao: EmployeeDao) : EmployeeItemsRepository {

    override suspend fun getEmployeeItemsAPI(): Resource<ArrayList<Employee>> {
        val employeeItems = RetrofitBuilder.getInstance().create(GetEmployeeItemsService::class.java)

        return try {
            val result = employeeItems.getEmployees()
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

    override suspend fun getEmployeeItems(): Resource<ArrayList<Employee>> {

        return try {
            val data = employeeDao.getEmployees().map { it.toEmployee() } as ArrayList<Employee>
            Resource.Success(data)
        } catch (e: Exception) {
            Resource.Error(
                statusCode = StatusCode.DATABASE_ERROR,
                message = e.message
            )
        }
    }

    override suspend fun filterByKeywordASC(s: String): Resource<ArrayList<Employee>> {

        return try {
            val data = employeeDao.filterByKeywordASC("%${s}%")
            val employeeList = data.map { it.toEmployee() } as ArrayList<Employee>
            Resource.Success(employeeList)
        } catch (e: Exception) {
            Resource.Error(
                statusCode = StatusCode.DATABASE_ERROR,
                message = e.message
            )
        }
    }

    override suspend fun filterByKeywordDESC(s: String): Resource<ArrayList<Employee>> {

        return try {
            val data = employeeDao.filterByKeywordDESC("%${s}%")
            val employeeList = data.map { it.toEmployee() } as ArrayList<Employee>
            Resource.Success(employeeList)
        } catch (e: Exception) {
            Resource.Error(
                statusCode = StatusCode.DATABASE_ERROR,
                message = e.message
            )
        }
    }

    override suspend fun saveEmployeeItem(employeeList: ArrayList<Employee>): Resource<Unit> {

        return try {
            employeeDao.saveEmployeeItem(employeeList.map { it.toEmployeeTable() })
            Resource.Success(null)
        } catch (e: Exception) {
            Resource.Error(
                statusCode = StatusCode.DATABASE_ERROR,
                message = e.message
            )
        }
    }

}