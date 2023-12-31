package com.khomenok.monitorschedule.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.khomenok.monitorschedule.domain.models.Faculty

@Entity
data class FacultyTable (
    @PrimaryKey val id: Int,
    @ColumnInfo val name: String,
    @ColumnInfo val abbr: String
) {

    fun toFaculty() = Faculty(
        id = id,
        name = name,
        abbrev = abbr
    )

}