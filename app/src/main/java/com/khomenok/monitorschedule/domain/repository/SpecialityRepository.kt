package com.khomenok.monitorschedule.domain.repository

import com.khomenok.monitorschedule.data.db.dao.SpecialityDao
import com.khomenok.monitorschedule.domain.models.Speciality
import com.khomenok.monitorschedule.domain.utils.Resource

interface SpecialityRepository {

    val specialityDao: SpecialityDao

    suspend fun getSpecialitiesAPI(): Resource<ArrayList<Speciality>>

    suspend fun getSpecialities(): Resource<ArrayList<Speciality>>

    suspend fun getSpecialityById(specialityId: Int): Resource<Speciality>

    suspend fun saveSpecialities(specialities: ArrayList<Speciality>): Resource<Unit>

}