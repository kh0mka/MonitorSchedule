package com.khomenok.monitorschedule.domain.models

data class LoadingStatus(
    val code: Int
) {

    companion object {
        const val LOAD_SCHEDULE = 0
    }

}