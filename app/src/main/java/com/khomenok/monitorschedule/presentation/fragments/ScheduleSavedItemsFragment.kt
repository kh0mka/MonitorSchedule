package com.khomenok.monitorschedule.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.khomenok.monitorschedule.R
import com.khomenok.monitorschedule.databinding.FragmentScheduleSavedItemsBinding
import com.khomenok.monitorschedule.domain.models.LoadingStatus
import com.khomenok.monitorschedule.domain.models.SavedSchedule
import com.khomenok.monitorschedule.presentation.adapters.SavedItemsAdapter
import com.khomenok.monitorschedule.presentation.dialogs.LoadingDialog
import com.khomenok.monitorschedule.presentation.dialogs.RenameScheduleDialog
import com.khomenok.monitorschedule.presentation.dialogs.StateDialog
import com.khomenok.monitorschedule.presentation.dialogs.DeleteScheduleDialog
import com.khomenok.monitorschedule.presentation.popupMenu.SavedSchedulePopupMenu
import com.khomenok.monitorschedule.presentation.utils.ErrorMessage
import com.khomenok.monitorschedule.presentation.viewModels.EmployeeItemsViewModel
import com.khomenok.monitorschedule.presentation.viewModels.GroupItemsViewModel
import com.khomenok.monitorschedule.presentation.viewModels.ScheduleViewModel
import com.khomenok.monitorschedule.presentation.viewModels.SavedSchedulesViewModel
import org.koin.androidx.navigation.koinNavGraphViewModel

// Saved items
class ScheduleSavedItemsFragment : Fragment() {

    private val groupItemsVM: GroupItemsViewModel by koinNavGraphViewModel(R.id.navigation)
    private val employeeItemsVM: EmployeeItemsViewModel by koinNavGraphViewModel(R.id.navigation)
    private val savedScheduleVM: SavedSchedulesViewModel by koinNavGraphViewModel(R.id.navigation)
    private val groupScheduleVM: ScheduleViewModel by koinNavGraphViewModel(R.id.navigation)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentScheduleSavedItemsBinding.inflate(inflater)

        val loadingStatus = LoadingStatus(LoadingStatus.LOAD_SCHEDULE)
        val dialog = LoadingDialog(loadingStatus)
        dialog.isCancelable = false

        val onSelectSchedule = { savedSchedule: SavedSchedule ->
            groupScheduleVM.selectSchedule(savedSchedule.id)
            Navigation.findNavController(binding.root).navigate(R.id.action_to_main_schedules)
        }

        val deleteSchedule = { savedSchedule: SavedSchedule ->
            groupScheduleVM.deleteSchedule(savedSchedule)
            if (savedSchedule.isGroup) {
                savedSchedule.group.isSaved = false
                groupItemsVM.saveGroupItem(savedSchedule.group)
            } else {
                savedSchedule.employee.isSaved = false
                employeeItemsVM.saveEmployeeItem(savedSchedule.employee)
            }
        }

        val deleteWarning = { savedSchedule: SavedSchedule ->
            val deleteScheduleDialog = DeleteScheduleDialog(savedSchedule = savedSchedule, agreeCallback = deleteSchedule)
            deleteScheduleDialog.show(parentFragmentManager, "WarningDialog")
        }

        val onRenameSubmit = { savedSchedule: SavedSchedule, newTitle: String ->
            if (savedSchedule.isGroup) {
                savedSchedule.group.title = newTitle
                groupItemsVM.saveGroupItem(savedSchedule.group)
            } else {
                savedSchedule.employee.title = newTitle
                employeeItemsVM.saveEmployeeItem(savedSchedule.employee)
            }
            savedScheduleVM.updateSchedule(savedSchedule)
            groupScheduleVM.renameSchedule(savedSchedule.id, newTitle)
        }

        val onRenameClick = { savedSchedule: SavedSchedule ->
            val stateDialog = RenameScheduleDialog(
                savedSchedule = savedSchedule,
                onRenameSubmit = onRenameSubmit
            )
            stateDialog.isCancelable = true
            stateDialog.show(parentFragmentManager, "RenameSchedule")
        }

        val updateSchedule = { savedSchedule: SavedSchedule ->
            savedScheduleVM.setActiveSchedule(savedSchedule)
            if (savedSchedule.isGroup) {
                groupScheduleVM.getGroupScheduleAPI(savedSchedule.group, true)
            } else {
                groupScheduleVM.getEmployeeScheduleAPI(savedSchedule.employee, true)
            }
        }

        val longPressLambda = { savedSchedule: SavedSchedule, view: View ->
            val popupMenu = SavedSchedulePopupMenu(
                context!!,
                savedSchedule = savedSchedule,
                delete = deleteWarning,
                update = updateSchedule,
                rename = onRenameClick
            ).initPopupMenu(view)

            popupMenu.show()
        }

        binding.nestedFilter.editText.isSaveEnabled(false)
        binding.nestedFilter.editText.setTextChangeListener {
            savedScheduleVM.filterByKeyword(it, true)
        }

        binding.scheduleSavedItemsRecycler.layoutManager = LinearLayoutManager(context)
        val adapter = SavedItemsAdapter(context!!, ArrayList(), onSelectSchedule, longPressLambda)
        binding.scheduleSavedItemsRecycler.adapter = adapter

        groupScheduleVM.deletedScheduleStatus.observe(viewLifecycleOwner) { savedSchedule ->
            if (savedSchedule != null) {
                savedScheduleVM.deleteSchedule(savedSchedule)
                savedScheduleVM.updateSavedSchedulesCount()
                groupScheduleVM.setDeletedScheduleNull()
                adapter.removeItem(savedSchedule)
            }
        }

        savedScheduleVM.updatedScheduleStatus.observe(viewLifecycleOwner) { updatedSchedule ->
            if (updatedSchedule != null) {
                adapter.updateItem(updatedSchedule)
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

        groupScheduleVM.successStatus.observe(viewLifecycleOwner) { successCode ->
            if (successCode != null) {
                val messageManager = ErrorMessage(context!!).get(successCode)
                groupScheduleVM.setSuccessNull()
                Toast.makeText(context, messageManager.title, Toast.LENGTH_SHORT).show()
            }
        }

        groupScheduleVM.loadingStatus.observe(viewLifecycleOwner) { loading ->
            if (loading) {
                dialog.show(parentFragmentManager, "LoadingDialog")
            } else {
                if (dialog.dialog?.isShowing == true) {
                    dialog.dismiss()
                }
            }
        }

        savedScheduleVM.errorMessageStatus.observe(viewLifecycleOwner) { errorStatus ->
            if (errorStatus != null) {
                val stateDialog = StateDialog(errorStatus)
                stateDialog.isCancelable = true
                stateDialog.show(parentFragmentManager, "ErrorDialog")
                savedScheduleVM.closeError()
            }
        }

        savedScheduleVM.activeScheduleStatusCount.observe(viewLifecycleOwner) { savedSchedulesCount ->
            if (savedSchedulesCount == null) return@observe
            val pluralSchedules = resources.getQuantityString(R.plurals.plural_schedules, savedSchedulesCount, savedSchedulesCount)
            binding.nestedFilter.filterAmount.text = pluralSchedules
        }

        savedScheduleVM.savedSchedulesStatus.observe(viewLifecycleOwner) { savedSchedules ->
            if (savedSchedules == null) {
                binding.hiddenPlaceholder.visibility = View.VISIBLE
                binding.savedSchedules.visibility = View.GONE
            } else {
                binding.savedSchedules.visibility = View.VISIBLE
                binding.hiddenPlaceholder.visibility = View.GONE
                adapter.updateItems(savedSchedules)
                binding.scheduleSavedItemsRecycler.alpha = 0f
                binding.scheduleSavedItemsRecycler.animate().alpha(1f).setDuration(300).start()
            }
        }

        return binding.root
    }

}