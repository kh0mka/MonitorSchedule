package com.khomenok.monitorschedule.data.repository

import androidx.core.text.isDigitsOnly
import com.khomenok.monitorschedule.api.RetrofitBuilder
import com.khomenok.monitorschedule.api.services.GetGroupItemsService
import com.khomenok.monitorschedule.data.db.dao.GroupDao
import com.khomenok.monitorschedule.data.db.entities.GroupTable
import com.khomenok.monitorschedule.domain.models.Group
import com.khomenok.monitorschedule.domain.repository.GroupItemsRepository
import com.khomenok.monitorschedule.domain.utils.Resource
import com.khomenok.monitorschedule.domain.utils.StatusCode

class GroupItemsRepositoryImpl(override val groupDao: GroupDao) : GroupItemsRepository {

    override suspend fun getGroupItemsAPI(): Resource<ArrayList<Group>> {
        val groupItemsService = RetrofitBuilder.getInstance().create(GetGroupItemsService::class.java)

        return try {
            val result = groupItemsService.getGroupItems()
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

    override suspend fun getAllGroupItems(): Resource<ArrayList<Group>> {
        return try {
            val data = groupDao.getAllGroups()
            val groupList = data.map { it.toGroup() } as ArrayList<Group>
            Resource.Success(groupList)
        } catch (e: Exception) {
            Resource.Error(
                statusCode = StatusCode.DATABASE_ERROR,
                message = e.message
            )
        }
    }

    override suspend fun filterByKeywordASC(s: String): Resource<ArrayList<Group>> {
        return try {
            val data: List<GroupTable> = if (s.length == 1 && s.isDigitsOnly()) {
                val res = groupDao.filterByCourseASC(s.toInt())
                res.ifEmpty {
                    groupDao.filterByKeywordASC("%${s}%")
                }
            } else {
                groupDao.filterByKeywordASC("%${s}%")
            }
            val groupList = data.map { it.toGroup() } as ArrayList<Group>
            Resource.Success(groupList)
        } catch (e: Exception) {
            Resource.Error(
                statusCode = StatusCode.DATABASE_ERROR,
                message = e.message
            )
        }
    }

    override suspend fun filterByKeywordDESC(s: String): Resource<ArrayList<Group>> {
        return try {
            val data: List<GroupTable> = if (s.length == 1 && s.isDigitsOnly()) {
                val res = groupDao.filterByCourseDESC(s.toInt())
                res.ifEmpty {
                    groupDao.filterByKeywordDESC("%${s}%")
                }
            } else {
                groupDao.filterByKeywordDESC("%${s}%")
            }
            val groupList = data.map { it.toGroup() } as ArrayList<Group>
            Resource.Success(groupList)
        } catch (e: Exception) {
            Resource.Error(
                statusCode = StatusCode.DATABASE_ERROR,
                message = e.message
            )
        }
    }

    override suspend fun saveGroupItemsList(groups: ArrayList<Group>): Resource<Unit> {
        return try {
            val groupList = groups.map { it.toGroupTable() }
            groupDao.saveAllGroups(groupList)
            Resource.Success(null)
        } catch (e: Exception) {
            Resource.Error(
                statusCode = StatusCode.DATABASE_ERROR,
                message = e.message
            )
        }
    }

}