package com.khomenok.monitorschedule.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.khomenok.monitorschedule.R
import com.khomenok.monitorschedule.databinding.FragmentScheduleItemsBinding
import com.khomenok.monitorschedule.presentation.dialogs.StateDialog
import com.khomenok.monitorschedule.presentation.utils.ErrorMessage
import com.khomenok.monitorschedule.presentation.viewModels.EmployeeItemsViewModel
import com.khomenok.monitorschedule.presentation.viewModels.GroupItemsViewModel
import com.khomenok.monitorschedule.presentation.viewModels.ScheduleViewModel
import org.koin.androidx.navigation.koinNavGraphViewModel

class ScheduleItemsFragment : Fragment() {

    private val groupScheduleVM: ScheduleViewModel by koinNavGraphViewModel(R.id.navigation)
    private val employeeItemsVM: EmployeeItemsViewModel by koinNavGraphViewModel(R.id.navigation)
    private val groupItemsVM: GroupItemsViewModel by koinNavGraphViewModel(R.id.navigation)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentScheduleItemsBinding.inflate(inflater)

        groupItemsVM.getAllGroupItems()
        employeeItemsVM.getAllEmployeeItems()

        binding.cancelButton.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_to_saved_schedules)
        }

        groupScheduleVM.successStatus.observe(viewLifecycleOwner) { successCode ->
            if (successCode != null) {
                val messageManager = ErrorMessage(context!!).get(successCode)
                groupScheduleVM.setSuccessNull()
                Toast.makeText(context, messageManager.title, Toast.LENGTH_SHORT).show()
            }
        }

        groupScheduleVM.errorStatus.observe(viewLifecycleOwner) { errorStatus ->
            if (errorStatus != null) {
                val stateDialog = StateDialog(errorStatus)
                stateDialog.isCancelable = true
                stateDialog.show(parentFragmentManager, "ErrorDialog")
                groupScheduleVM.closeError()
            }
        }

        return binding.root
    }

}