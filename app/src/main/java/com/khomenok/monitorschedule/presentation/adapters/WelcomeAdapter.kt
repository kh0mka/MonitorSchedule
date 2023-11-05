package com.khomenok.monitorschedule.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.khomenok.monitorschedule.databinding.WelcomePageItemBinding
import com.khomenok.monitorschedule.domain.models.WelcomeText

class WelcomeAdapter(
    private val context: Context,
    private val data: ArrayList<WelcomeText>,
): RecyclerView.Adapter<WelcomeAdapter.ViewHolder>() {

    class ViewHolder(
        private val binding: WelcomePageItemBinding,
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: WelcomeText) {
            binding.title.text = item.title
            binding.caption.text = item.caption
            binding.icon.setImageDrawable(item.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = WelcomePageItemBinding.inflate(LayoutInflater.from(context), parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        holder.bind(item)
    }

    override fun getItemCount() = data.size

}