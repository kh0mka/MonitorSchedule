package com.khomenok.monitorschedule.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.khomenok.monitorschedule.data.db.entities.FacultyTable

@Dao
interface FacultyDao {

    @Query("SELECT * FROM FacultyTable")
    fun getFaculties(): List<FacultyTable>

    @Query("SELECT * FROM FacultyTable WHERE id=:facultyID")
    fun getFacultyById(facultyID: Int): FacultyTable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveFaculties(faculties: List<FacultyTable>)

}