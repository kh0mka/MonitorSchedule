package com.khomenok.monitorschedule.presentation.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.khomenok.monitorschedule.presentation.fragments.AllEmployeeItemsFragment
import com.khomenok.monitorschedule.presentation.fragments.AllGroupItemsFragment

class ScheduleItemsVPAdapter(fa: FragmentActivity): FragmentStateAdapter(fa) {

    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AllGroupItemsFragment()
            1 -> AllEmployeeItemsFragment()
            else -> AllEmployeeItemsFragment()
        }
    }

}