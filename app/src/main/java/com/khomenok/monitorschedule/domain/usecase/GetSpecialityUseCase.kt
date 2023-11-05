package com.khomenok.monitorschedule.domain.usecase

import com.khomenok.monitorschedule.domain.models.Speciality
import com.khomenok.monitorschedule.domain.repository.SpecialityRepository
import com.khomenok.monitorschedule.domain.utils.Resource

class GetSpecialityUseCase(private val specialityRepository: SpecialityRepository) {

    suspend fun getSpecialitiesAPI(): Resource<ArrayList<Speciality>> {
        return specialityRepository.getSpecialitiesAPI()
    }

    suspend fun getSpecialities(): Resource<ArrayList<Speciality>> {
        return specialityRepository.getSpecialities()
    }

    suspend fun getSpecialityById(specialityId: Int): Resource<Speciality> {
        return specialityRepository.getSpecialityById(specialityId)
    }

    suspend fun saveSpecialities(specialities: ArrayList<Speciality>): Resource<Unit> {
        return specialityRepository.saveSpecialities(specialities)
    }

}