package com.khomenok.monitorschedule.domain.usecase

import com.khomenok.monitorschedule.domain.models.Faculty
import com.khomenok.monitorschedule.domain.repository.FacultyRepository
import com.khomenok.monitorschedule.domain.utils.Resource

class GetFacultyUseCase(private val facultyRepository: FacultyRepository) {

    suspend fun getFacultiesAPI(): Resource<ArrayList<Faculty>> {
        return facultyRepository.getFacultiesAPI()
    }

    suspend fun getFaculties(): Resource<ArrayList<Faculty>> {
        return facultyRepository.getFaculties()
    }

    suspend fun getFacultyById(facultyId: Int): Resource<Faculty> {
        return facultyRepository.getFacultyById(facultyId)
    }

    suspend fun saveFaculties(faculties: ArrayList<Faculty>): Resource<Unit> {
        return facultyRepository.saveFaculties(faculties)
    }

}