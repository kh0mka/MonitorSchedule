package com.khomenok.monitorschedule.domain.models

import com.khomenok.monitorschedule.domain.utils.StatusCode

data class StateStatus(
    val state: Int,
    val type: StatusCode,
    val message: String? = ""
) {

    companion object {
        const val SUCCESS_STATE = 1
        const val ERROR_STATE = 2
        const val INFO_STATE = 3
    }

}