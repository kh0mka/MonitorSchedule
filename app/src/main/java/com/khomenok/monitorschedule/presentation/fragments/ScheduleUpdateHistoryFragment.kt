package com.khomenok.monitorschedule.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.khomenok.monitorschedule.R
import com.khomenok.monitorschedule.databinding.FragmentScheduleUpdateHistoryBinding
import com.khomenok.monitorschedule.domain.models.ScheduleSubject
import com.khomenok.monitorschedule.presentation.dialogs.StateDialog
import com.khomenok.monitorschedule.presentation.dialogs.SubjectDialog
import com.khomenok.monitorschedule.presentation.viewModels.ScheduleUpdatedHistoryViewModel
import com.khomenok.monitorschedule.presentation.viewModels.ScheduleViewModel
import org.koin.androidx.navigation.koinNavGraphViewModel
import java.util.*

class ScheduleUpdateHistoryFragment : Fragment() {

    private val groupScheduleVM: ScheduleViewModel by koinNavGraphViewModel(R.id.navigation)
    private val updatedActionsVM: ScheduleUpdatedHistoryViewModel by koinNavGraphViewModel(R.id.navigation)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentScheduleUpdateHistoryBinding.inflate(inflater)

        groupScheduleVM.scheduleStatus.observe(viewLifecycleOwner) {
            binding.scheduleHeaderView.setTitle(it.getTitle())
            binding.scheduleHeaderView.setDescription(it.getDescription())
            binding.scheduleHeaderView.setImage(it.getImage())
        }

        val onShowSubjectDialog = { subject: ScheduleSubject ->
            val subjectDialog = SubjectDialog(subject, null)
            subjectDialog.isCancelable = true
            subjectDialog.show(parentFragmentManager, "subjectDialog")
        }

        binding.cancelButton.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_scheduleUpdateHistoryFragment_to_mainScheduleFragment)
        }

        updatedActionsVM.errorStatus.observe(viewLifecycleOwner) { errorStatus ->
            if (errorStatus != null) {
                val stateDialog = StateDialog(errorStatus)
                stateDialog.isCancelable = true
                stateDialog.show(parentFragmentManager, "ErrorDialog")
                updatedActionsVM.closeError()
            }
        }

        binding.scheduleUpdateHistoryRecycler.visibility = View.GONE
        binding.noUpdatedPlaceholder.visibility = View.VISIBLE

        return binding.root
    }

}