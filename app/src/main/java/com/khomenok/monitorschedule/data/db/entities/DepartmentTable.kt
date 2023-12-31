package com.khomenok.monitorschedule.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.khomenok.monitorschedule.domain.models.Department

@Entity
data class DepartmentTable(
    @PrimaryKey val id: Int,
    @ColumnInfo val name: String,
    @ColumnInfo val abbr: String
) {

    fun toDepartment() = Department(
        id = id,
        name = name,
        abbrev = abbr
    )

}