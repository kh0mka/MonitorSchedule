package com.khomenok.monitorschedule.api.services

import com.khomenok.monitorschedule.domain.models.Employee
import retrofit2.Response
import retrofit2.http.GET

interface GetEmployeeItemsService {

    @GET("employees/all")
    suspend fun getEmployees(): Response<ArrayList<Employee>>

}