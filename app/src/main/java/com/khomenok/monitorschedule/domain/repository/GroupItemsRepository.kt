package com.khomenok.monitorschedule.domain.repository

import com.khomenok.monitorschedule.data.db.dao.GroupDao
import com.khomenok.monitorschedule.domain.models.Group
import com.khomenok.monitorschedule.domain.utils.Resource

interface GroupItemsRepository {

    val groupDao: GroupDao

    suspend fun getGroupItemsAPI(): Resource<ArrayList<Group>>

    suspend fun getAllGroupItems(): Resource<ArrayList<Group>>

    suspend fun filterByKeywordASC(s: String): Resource<ArrayList<Group>>

    suspend fun filterByKeywordDESC(s: String): Resource<ArrayList<Group>>

    suspend fun saveGroupItemsList(groups: ArrayList<Group>): Resource<Unit>

}