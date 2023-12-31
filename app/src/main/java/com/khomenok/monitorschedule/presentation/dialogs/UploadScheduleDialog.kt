package com.khomenok.monitorschedule.presentation.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.khomenok.monitorschedule.R
import com.khomenok.monitorschedule.databinding.UploadScheduleDialogBinding
import com.khomenok.monitorschedule.domain.models.SavedSchedule

class UploadScheduleDialog(
    private val savedSchedule: SavedSchedule,
    private val onUploadSubmit: (savedSchedule: SavedSchedule) -> Unit
) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = UploadScheduleDialogBinding.inflate(inflater)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.dialog_bg_shadow)

        val titleText = if (savedSchedule.isGroup) {
            getString(R.string.upload_schedule_dialog_title, savedSchedule.group.name)
        } else {
            getString(R.string.upload_schedule_dialog_title, savedSchedule.employee.getFullName())
        }
        binding.title.text = titleText

        binding.agreeButton.setOnClickListener {
            onUploadSubmit(savedSchedule)
            dismiss()
        }

        binding.cancelButton.setOnClickListener {
            dismiss()
        }

        return binding.root
    }

}