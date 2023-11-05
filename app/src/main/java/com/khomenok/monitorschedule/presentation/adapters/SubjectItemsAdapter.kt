package com.khomenok.monitorschedule.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.khomenok.monitorschedule.R
import com.khomenok.monitorschedule.databinding.SubjectItemSourceBinding
import com.khomenok.monitorschedule.domain.models.SavedSchedule

class SubjectItemsAdapter(
    private val context: Context,
    private val items: ArrayList<SavedSchedule>,
    private val onClick: (savedSchedule: SavedSchedule) -> Unit,
): RecyclerView.Adapter<SubjectItemsAdapter.ViewHolder>() {

    class ViewHolder(
        private val binding: SubjectItemSourceBinding,
        private val onClick: (savedSchedule: SavedSchedule) -> Unit,
        ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(context: Context, item: SavedSchedule) {
            val courseText = context.getString(R.string.course)
            if (item.isGroup) {
                val group = item.group
                binding.nestedGroup.title.text = group.name
            } else {
                val employee = item.employee
                binding.nestedGroup.title.text = employee.getFullName()
            }

            binding.root.setOnClickListener {
                onClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = SubjectItemSourceBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.bind(context, item)
    }

    override fun getItemCount() = items.size

}