package com.khomenok.monitorschedule.api.services

import com.khomenok.monitorschedule.domain.models.Group
import retrofit2.Response
import retrofit2.http.GET

interface GetGroupItemsService {

    @GET("student-groups")
    suspend fun getGroupItems(): Response<ArrayList<Group>>

}