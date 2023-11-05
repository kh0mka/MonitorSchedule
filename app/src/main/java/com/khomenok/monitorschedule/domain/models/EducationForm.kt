package com.khomenok.monitorschedule.domain.models

import com.khomenok.monitorschedule.data.db.entities.EducationFormTable

data class EducationForm (
    val id: Int,
    val name: String
) {

    companion object {
        val empty = EducationForm(
            id = -1,
            name = ""
        )
    }

    fun toEducationFormTable() = EducationFormTable(
        id = id,
        name = name
    )

}