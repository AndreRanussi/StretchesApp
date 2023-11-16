package com.course.stretchesapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.course.stretchesapp.databinding.ItemHistoryRowBinding

class HistoryAdapter(private val items: ArrayList<String>):RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    inner class HistoryViewHolder(binding: ItemHistoryRowBinding) : RecyclerView.ViewHolder(binding.root) {
        val rlHistoryMain = binding.rlHistoryMain
        val tvId = binding.tvId
        val tvDate = binding.tvDate
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(ItemHistoryRowBinding.inflate(LayoutInflater.from(parent.context),
            parent, false
            ))
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
       val date: String = items[position]
        holder.tvId.text = "${position+1}"
        holder.tvDate.text = date

        if(position % 2 == 0) {
            holder.rlHistoryMain.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.lightGrey))
        } else {
            holder.rlHistoryMain.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
        }

    }

    override fun getItemCount(): Int {
        return items.size

    }
}