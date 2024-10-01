package com.neverland.thinkerbell.view.myPage.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.neverland.domain.model.univ.RecentBookmarkSchedule
import com.neverland.thinkerbell.databinding.ItemFavoriteScheduleBinding
import java.text.SimpleDateFormat
import java.util.Locale

class MyPageFavoriteScheduleAdapter(private val schedules: List<RecentBookmarkSchedule>) :
    RecyclerView.Adapter<MyPageFavoriteScheduleAdapter.MyPageFavoriteScheduleViewHolder>(){

    inner class MyPageFavoriteScheduleViewHolder(private val binding: ItemFavoriteScheduleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(schedule: RecentBookmarkSchedule) {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())

            try {
                val date = inputFormat.parse(schedule.startDate)
                val formattedDate = date?.let { outputFormat.format(it) }
                binding.tvScheduleDate.text = formattedDate // 연.월.일 형식으로 변환
            } catch (e: Exception) {
                e.printStackTrace()
                binding.tvScheduleDate.text = schedule.startDate // 파싱 실패 시 원본 날짜 표시
            }
            binding.tvScheduleTitle.text = schedule.title
            binding.btnFavorite.visibility = View.GONE
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyPageFavoriteScheduleViewHolder {
        val binding = ItemFavoriteScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyPageFavoriteScheduleViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return schedules.size
    }

    override fun onBindViewHolder(holder: MyPageFavoriteScheduleViewHolder, position: Int) {
        holder.bind(schedules[position])
    }


}