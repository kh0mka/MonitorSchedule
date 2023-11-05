package com.khomenok.monitorschedule.domain.models

import com.khomenok.monitorschedule.data.db.entities.DepartmentTable

data class Department(
    val id: Int,
    val name: String,
    val abbrev: String
) {

    fun toDepartmentTable() = DepartmentTable(
        id = id,
        name = name,
        abbr = abbrev
    )

}