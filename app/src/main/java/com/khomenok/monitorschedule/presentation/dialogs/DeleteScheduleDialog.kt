package com.khomenok.monitorschedule.presentation.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.khomenok.monitorschedule.R
import com.khomenok.monitorschedule.databinding.DeleteScheduleDialogBinding
import com.khomenok.monitorschedule.domain.models.SavedSchedule

class DeleteScheduleDialog(
    private val savedSchedule: SavedSchedule,
    private val agreeCallback: (savedSchedule: SavedSchedule) -> Unit,
) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DeleteScheduleDialogBinding.inflate(inflater)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.dialog_bg_shadow)

        isCancelable = true
        val deleteText = if (savedSchedule.isGroup) {
            resources.getString(R.string.delete_schedule, savedSchedule.group.name)
        } else {
            resources.getString(R.string.delete_schedule, savedSchedule.employee.getFullName())
        }

        binding.title.text = deleteText

        binding.agreeButton.setOnClickListener {
            agreeCallback(savedSchedule)
            dismiss()
        }

        binding.cancelButton.setOnClickListener {
            dismiss()
        }

        return binding.root
    }

}


