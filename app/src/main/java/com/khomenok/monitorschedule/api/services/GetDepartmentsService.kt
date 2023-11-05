package com.khomenok.monitorschedule.api.services

import com.khomenok.monitorschedule.domain.models.Department
import retrofit2.Response
import retrofit2.http.GET

interface GetDepartmentsService {

    @GET("departments")
    suspend fun getDepartments(): Response<ArrayList<Department>>

}