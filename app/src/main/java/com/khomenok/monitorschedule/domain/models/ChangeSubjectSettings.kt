package com.khomenok.monitorschedule.domain.models

data class ChangeSubjectSettings (
    val forAll: Boolean,
    val forOnlyType: Boolean,
    val forOnlyPeriod: Boolean,
    val forOnlySubgroup: Boolean
)