package com.khomenok.monitorschedule.presentation.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.khomenok.monitorschedule.R
import com.khomenok.monitorschedule.databinding.ActiveScheduleDialogBinding
import com.khomenok.monitorschedule.domain.models.SavedSchedule
import com.khomenok.monitorschedule.domain.models.Schedule
import com.khomenok.monitorschedule.presentation.utils.SubjectManager

class ScheduleDialog(
    private val schedule: Schedule,
    private val update: (savedSchedule: SavedSchedule) -> Unit,
    private val delete: (savedSchedule: SavedSchedule) -> Unit
): DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = ActiveScheduleDialogBinding.inflate(inflater)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.dialog_bg_shadow)

        val lastUpdateText = resources.getString(R.string.last_update, schedule.getLastUpdateText())
        val selectedSubgroup = schedule.settings.subgroup.selectedNum
        binding.lastUpdate.text = lastUpdateText
        binding.scheduleSubgroup.text = if (selectedSubgroup == 0) {
            resources.getString(R.string.selected_all_subgroups)
        } else {
            resources.getString(R.string.selected_subgroup, selectedSubgroup)
        }

        val examsDatePeriod = if (!schedule.isExamsNotExist()) {
            resources.getString(
                R.string.exams_date_period,
                schedule.getDateText(schedule.startExamsDate),
                schedule.getDateText(schedule.endExamsDate)
            )
        } else {
            resources.getString(R.string.exams_empty_date_period)
        }
        binding.examsDate.text = examsDatePeriod

        val scheduleDatePeriod = if (!schedule.isScheduleNotExist()) {
            resources.getString(
                R.string.schedule_date_period,
                schedule.getDateText(schedule.startDate),
                schedule.getDateText(schedule.endDate)
            )
        } else {
            resources.getString(R.string.schedule_empty_date_period)
        }
        binding.scheduleDate.text = scheduleDatePeriod

        if (schedule.isGroup()) {
            val group = schedule.group
            binding.schedule.title.text = group.name
            binding.scheduleSubtitles.text = group.getFacultyAndSpecialityFull()
        } else {
            val employee = schedule.employee
            if (employee.departmentsList.isNullOrEmpty()) {
                val noDepartments = resources.getString(R.string.active_schedule_no_departments)
                binding.scheduleSubtitles.text = noDepartments
            } else {
                binding.scheduleSubtitles.text = employee.getFullDepartments("\n\n")
            }
        }

        if (schedule.subjectNow != null) {
            val subjectManager = SubjectManager(schedule.subjectNow!!, context!!)
            binding.scheduleLocationNow.text = subjectManager.getSubjectDate()
        } else {
            val subjectNowText = resources.getString(R.string.no_subject_now)
            binding.scheduleLocationNow.text = subjectNowText
        }

        binding.deleteButton.setOnClickListener {
            delete(schedule.toSavedSchedule())
            dismiss()
        }

        binding.updateButton.setOnClickListener {
            update(schedule.toSavedSchedule())
            dismiss()
        }

        return binding.root
    }

}