package com.khomenok.monitorschedule.data.db.dao

import androidx.room.*
import com.khomenok.monitorschedule.data.db.entities.EmployeeTable

@Dao
interface EmployeeDao {

    @Query("SELECT * FROM EmployeeTable ORDER BY fullName")
    fun getEmployees(): List<EmployeeTable>

    @Query("SELECT * FROM EmployeeTable WHERE fullName LIKE :s " +
            "OR title LIKE :s " +
            "OR degree LIKE :s " +
            "OR degreeAbbrev LIKE :s " +
            "OR departments LIKE :s " +
            "OR rank LIKE :s " +
            "OR academicDepartment LIKE :s ORDER BY fullName ASC")
    fun filterByKeywordASC(s: String): List<EmployeeTable>

    @Query("SELECT * FROM EmployeeTable WHERE fullName LIKE :s " +
            "OR title LIKE :s " +
            "OR degree LIKE :s " +
            "OR degreeAbbrev LIKE :s " +
            "OR departments LIKE :s " +
            "OR rank LIKE :s " +
            "OR academicDepartment LIKE :s ORDER BY fullName DESC")
    fun filterByKeywordDESC(s: String): List<EmployeeTable>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveEmployeeItem(employeeList: List<EmployeeTable>)

}