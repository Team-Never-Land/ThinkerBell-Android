package com.neverland.thinkerbell.view.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.neverland.domain.model.univ.AcademicSchedule
import com.neverland.thinkerbell.databinding.ItemCalendarScheduleBinding
import com.neverland.thinkerbell.view.OnRvItemClickListener

class CalendarScheduleAdapter : RecyclerView.Adapter<CalendarScheduleAdapter.ScheduleViewHolder>() {

    private var schedules: List<AcademicSchedule> = listOf()

    fun updateSchedules(newSchedules: List<AcademicSchedule>) {
        schedules = newSchedules
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val binding = ItemCalendarScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ScheduleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        holder.bind(schedules[position])
    }

    override fun getItemCount(): Int {
        return schedules.size
    }

    inner class ScheduleViewHolder(private val binding: ItemCalendarScheduleBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AcademicSchedule){
            val date = if (item.startDate == item.endDate) item.startDate.removeRange(0,5).replace("-",".")
            else "${item.startDate.removeRange(0,5).replace("-",".")} ~ ${item.endDate.removeRange(0,5).replace("-",".")}"
            binding.tvScheduleDate.text = date
            binding.tvScheduleTitle.text = item.title

            binding.btnFavorite.isChecked = item.marked

            binding.btnFavorite.setOnClickListener {
                rvItemClickListener.onClick(Pair(item.id, binding.btnFavorite.isChecked))
            }
        }
    }

    private lateinit var rvItemClickListener: OnRvItemClickListener<Pair<Int, Boolean>>

    fun setRvItemClickListener(rvItemClickListener: OnRvItemClickListener<Pair<Int, Boolean>>){
        this.rvItemClickListener = rvItemClickListener
    }
}
