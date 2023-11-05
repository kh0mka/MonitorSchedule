package com.khomenok.monitorschedule.data.repository

import com.khomenok.monitorschedule.api.RetrofitBuilder
import com.khomenok.monitorschedule.api.services.GetSpecialitiesService
import com.khomenok.monitorschedule.data.db.dao.SpecialityDao
import com.khomenok.monitorschedule.domain.models.Speciality
import com.khomenok.monitorschedule.domain.repository.SpecialityRepository
import com.khomenok.monitorschedule.domain.utils.Resource
import com.khomenok.monitorschedule.domain.utils.StatusCode

class SpecialityRepositoryImpl(override val specialityDao: SpecialityDao) : SpecialityRepository {

    override suspend fun getSpecialitiesAPI(): Resource<ArrayList<Speciality>> {
        val specialitiesService = RetrofitBuilder.getInstance().create(GetSpecialitiesService::class.java)

        return try {
            val result = specialitiesService.getSpecialities()
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

    override suspend fun getSpecialities(): Resource<ArrayList<Speciality>> {

        return try {
            val result = specialityDao.getSpecialities()
            val specialitiesList = result.map { it.toSpeciality() } as ArrayList<Speciality>
            Resource.Success(specialitiesList)
        } catch (e: Exception) {
            Resource.Error(
                statusCode = StatusCode.DATABASE_ERROR,
                message = e.message
            )
        }
    }

    override suspend fun getSpecialityById(specialityId: Int): Resource<Speciality> {

        return try {
            val result = specialityDao.getSpecialitiesById(specialityId)
            Resource.Success(result.toSpeciality())
        } catch (e: Exception) {
            Resource.Error(
                statusCode = StatusCode.DATABASE_ERROR,
                message = e.message
            )
        }
    }

    override suspend fun saveSpecialities(specialities: ArrayList<Speciality>): Resource<Unit> {

        return try {
            val specialitiesList = specialities.map { it.toSpecialityTable() }
            specialityDao.saveSpecialities(specialitiesList)
            Resource.Success(null)
        } catch (e: Exception) {
            Resource.Error(
                statusCode = StatusCode.DATABASE_ERROR,
                message = e.message
            )
        }
    }

}