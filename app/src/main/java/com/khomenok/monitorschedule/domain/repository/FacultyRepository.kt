package com.khomenok.monitorschedule.domain.repository

import com.khomenok.monitorschedule.data.db.dao.FacultyDao
import com.khomenok.monitorschedule.domain.models.Faculty
import com.khomenok.monitorschedule.domain.utils.Resource

interface FacultyRepository {

    val facultyDao: FacultyDao

    suspend fun getFacultiesAPI(): Resource<ArrayList<Faculty>>

    suspend fun getFaculties(): Resource<ArrayList<Faculty>>

    suspend fun getFacultyById(facultyId: Int): Resource<Faculty>

    suspend fun saveFaculties(faculties: ArrayList<Faculty>): Resource<Unit>

}