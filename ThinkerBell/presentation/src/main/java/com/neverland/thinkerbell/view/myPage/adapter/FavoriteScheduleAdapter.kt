package com.neverland.thinkerbell.view.myPage.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.neverland.core.utils.LoggerUtil
import com.neverland.domain.model.univ.AcademicSchedule
import com.neverland.domain.model.univ.BookmarkScheduleGroup
import com.neverland.domain.model.univ.groupSchedulesByYearAndMonth
import com.neverland.thinkerbell.databinding.ItemFavoriteScheduleBinding
import com.neverland.thinkerbell.databinding.ItemFavoriteScheduleMonthlyHeaderBinding
import com.neverland.thinkerbell.view.OnRvItemClickListener

class FavoriteScheduleAdapter(private var scheduleGroups: List<BookmarkScheduleGroup>,
    private var allItems: List<BookmarkScheduleGroup> = listOf()
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_ITEM = 1
    }

    // 해당 포지션이 헤더인지 확인하는 메서드
    private fun isHeaderPosition(position: Int): Boolean {
        var count = 0
        scheduleGroups.forEach { group ->
            if (position == count) return true // 그룹의 시작점은 항상 헤더
            count += group.schedules.size + 1 // +1은 헤더 자체를 위한 공간
            if (position < count) return false // 헤더 이후의 항목들은 아이템
        }
        return false
    }

    // ViewType에 따라 헤더 또는 아이템을 결정
    override fun getItemViewType(position: Int): Int {
        return if (isHeaderPosition(position)) VIEW_TYPE_HEADER else VIEW_TYPE_ITEM
    }

    // 헤더 및 아이템의 ViewHolder를 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_HEADER) {
            val binding = ItemFavoriteScheduleMonthlyHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            HeaderViewHolder(binding)
        } else {
            val binding = ItemFavoriteScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ItemViewHolder(binding)
        }
    }

    // 총 아이템 수를 계산 (헤더 + 일정)
    override fun getItemCount(): Int {
        var count = 0
        scheduleGroups.forEach { count += it.schedules.size + 1 } // +1 for each header
        return count
    }

    // 각 항목에 대해 바인딩을 처리
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HeaderViewHolder) {
            val group = getScheduleGroupForPosition(position)
            holder.bind(group.month)
        } else if (holder is ItemViewHolder) {
            val schedule = getScheduleForPosition(position)
            holder.bind(schedule)
        }
    }

    // 주어진 위치에 해당하는 그룹(헤더)을 찾기
    private fun getScheduleGroupForPosition(position: Int): BookmarkScheduleGroup {
        var count = 0
        scheduleGroups.forEach { group ->
            if (position == count) return group // 헤더 위치에서 그룹 반환
            count += group.schedules.size + 1
        }
        throw IllegalArgumentException("Invalid position for header: $position")
    }

    // 주어진 위치에 해당하는 일정을 찾기
    private fun getScheduleForPosition(position: Int): AcademicSchedule {
        var count = 0
        scheduleGroups.forEach { group ->
            count += 1 // 헤더를 지나침
            if (position < count + group.schedules.size) {
                return group.schedules[position - count] // 현재 그룹에서의 아이템 위치 반환
            }
            count += group.schedules.size // 다음 그룹으로 이동
        }
        throw IllegalArgumentException("Invalid position for item: $position")
    }

    inner class HeaderViewHolder(private val binding: ItemFavoriteScheduleMonthlyHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(month: String) {
            binding.tvMonth.text = "${month.toInt()}월"
        }
    }

    inner class ItemViewHolder(private val binding: ItemFavoriteScheduleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(schedule: AcademicSchedule) {
            binding.tvScheduleTitle.text = schedule.title
            val date = if (schedule.startDate == schedule.endDate) schedule.startDate.removeRange(0,5).replace("-",".")
                else "${schedule.startDate.removeRange(0,5).replace("-",".")} ~ ${schedule.endDate.removeRange(0,5).replace("-",".")}"
            binding.tvScheduleDate.text = date
            binding.btnFavorite.isChecked = schedule.marked
            binding.btnFavorite.setOnClickListener {
                bookmarkClickListener.onClick(Pair(schedule.id, binding.btnFavorite.isChecked))
            }
        }
    }

    private lateinit var bookmarkClickListener: OnRvItemClickListener<Pair<Int, Boolean>>

    fun setBookmarkClickListener(bookmarkClickListener: OnRvItemClickListener<Pair<Int, Boolean>>){
        this.bookmarkClickListener = bookmarkClickListener
    }

    fun setList(newList: List<AcademicSchedule> = listOf(), year: Int) {
        LoggerUtil.d("${newList}, ${year}")
        // 데이터를 월별로 그룹화
        if (allItems.isEmpty()) {
            allItems = groupSchedulesByYearAndMonth(newList)
        }
        LoggerUtil.d(allItems.toString())
        // 월별로 그룹을 내림차순 정렬
        val sortedScheduleGroups = allItems.filter { it.year == year }.sortedByDescending { group ->
            // "9월"과 같은 문자열에서 숫자를 추출하여 정렬
            group.month
        }
        sortedScheduleGroups.map { it.schedules.map { it.marked = true } }
        LoggerUtil.d(sortedScheduleGroups.toString())
        scheduleGroups = sortedScheduleGroups
        LoggerUtil.d(scheduleGroups.toString())
        notifyDataSetChanged()
    }
}
