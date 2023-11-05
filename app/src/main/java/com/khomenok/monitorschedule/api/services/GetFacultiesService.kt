package com.khomenok.monitorschedule.api.services

import com.khomenok.monitorschedule.domain.models.Faculty
import retrofit2.Response
import retrofit2.http.GET

interface GetFacultiesService {

    @GET("faculties")
    suspend fun getFaculties(): Response<ArrayList<Faculty>>

}