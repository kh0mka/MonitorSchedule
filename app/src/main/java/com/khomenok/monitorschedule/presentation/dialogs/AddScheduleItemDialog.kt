package com.khomenok.monitorschedule.presentation.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.khomenok.monitorschedule.R
import com.khomenok.monitorschedule.databinding.AddScheduleItemDialogBinding
import com.khomenok.monitorschedule.domain.models.Employee
import com.khomenok.monitorschedule.domain.models.Group
import com.khomenok.monitorschedule.domain.models.SavedSchedule
import com.khomenok.monitorschedule.presentation.adapters.EmployeeItemsAdapter
import com.khomenok.monitorschedule.presentation.adapters.GroupItemsAdapter
import com.khomenok.monitorschedule.presentation.viewModels.EmployeeItemsViewModel
import com.khomenok.monitorschedule.presentation.viewModels.GroupItemsViewModel
import org.koin.androidx.navigation.koinNavGraphViewModel

class AddScheduleItemDialog(
    private val isGroup: Boolean,
    private val onSelectScheduleItem: (savedSchedule: SavedSchedule) -> Unit,
) : DialogFragment() {

    private val employeeItemsVM: EmployeeItemsViewModel by koinNavGraphViewModel(R.id.navigation)
    private val groupItemsVM: GroupItemsViewModel by koinNavGraphViewModel(R.id.navigation)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.dialog_bg_shadow)
        val binding = AddScheduleItemDialogBinding.inflate(inflater)
        val recyclerViewLayoutManager = LinearLayoutManager(context)

        val onSelectGroupItem = { group: Group ->
            onSelectScheduleItem(group.toSavedSchedule())
            dismiss()
        }

        val onSelectEmployeeItem = { employee: Employee ->
            onSelectScheduleItem(employee.toSavedSchedule())
            dismiss()
        }

        binding.searchSourceSchedule.setTextChangeListener {
            if (isGroup) {
                groupItemsVM.filterByKeyword(it)
            } else {
                employeeItemsVM.filterByKeyword(it)
            }
        }

        if (isGroup) {
            groupItemsVM.getAllGroupItems()
            val adapter = GroupItemsAdapter(requireContext(), arrayListOf(), onSelectGroupItem)
            binding.sourceRecycler.layoutManager = recyclerViewLayoutManager
            binding.sourceRecycler.adapter = adapter
            groupItemsVM.allGroupItemsStatus.observe(viewLifecycleOwner) { groupItems ->
                if (groupItems != null) {
                    adapter.setGroupList(groupItems)
                }
            }
        } else {
            employeeItemsVM.getAllEmployeeItems()
            val adapter = EmployeeItemsAdapter(requireContext(), arrayListOf(), onSelectEmployeeItem)
            binding.sourceRecycler.layoutManager = recyclerViewLayoutManager
            binding.sourceRecycler.adapter = adapter
            employeeItemsVM.employeeItemsStatus.observe(viewLifecycleOwner) { employeeItems ->
                if (employeeItems != null) {
                    adapter.setEmployeeList(employeeItems)
                }
            }
        }

        return binding.root
    }

}