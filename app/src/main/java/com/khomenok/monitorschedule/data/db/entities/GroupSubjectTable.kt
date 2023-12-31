package com.khomenok.monitorschedule.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.khomenok.monitorschedule.domain.models.GroupSubject

@Entity
data class GroupSubjectTable(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo val specialityName: String,
    @ColumnInfo val specialityCode: String,
    @ColumnInfo val numberOfStudents: Int,
    @ColumnInfo val name: String,
    @ColumnInfo val educationDegree: Int
) {

    fun toGroupSubject() = GroupSubject(
        specialityName = specialityName,
        specialityCode = specialityCode,
        numberOfStudents = numberOfStudents,
        name = name,
        educationDegree = educationDegree
    )

}