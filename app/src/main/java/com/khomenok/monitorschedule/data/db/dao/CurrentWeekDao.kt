package com.khomenok.monitorschedule.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.khomenok.monitorschedule.data.db.entities.CurrentWeekTable

@Dao
interface CurrentWeekDao {

    @Query("SELECT * FROM CurrentWeekTable")
    fun getCurrentWeek(): CurrentWeekTable?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCurrentWeek(currentWeek: CurrentWeekTable)

}