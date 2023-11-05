package com.khomenok.monitorschedule.api.services

import com.khomenok.monitorschedule.domain.models.Speciality
import retrofit2.Response
import retrofit2.http.GET

interface GetSpecialitiesService {

    @GET("specialities")
    suspend fun getSpecialities(): Response<ArrayList<Speciality>>

}