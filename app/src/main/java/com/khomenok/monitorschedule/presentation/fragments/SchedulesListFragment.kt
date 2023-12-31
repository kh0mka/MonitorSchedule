package com.khomenok.monitorschedule.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.khomenok.monitorschedule.R
import com.khomenok.monitorschedule.databinding.FragmentSchedulesListBinding
import com.khomenok.monitorschedule.presentation.viewModels.SavedSchedulesViewModel
import org.koin.androidx.navigation.koinNavGraphViewModel

// Fragment of schedules list
class SchedulesListFragment : Fragment() {

    private val savedScheduleVM: SavedSchedulesViewModel by koinNavGraphViewModel(R.id.navigation)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSchedulesListBinding.inflate(inflater)

        savedScheduleVM.getSavedSchedules()

        binding.cancelButton.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_to_main_schedules)
        }

        binding.addScheduleButton.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_to_schedules_items)
        }

        return binding.root
    }

}