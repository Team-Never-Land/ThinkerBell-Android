package com.neverland.thinkerbell.view.home

import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.neverland.domain.model.univ.AcademicSchedule
import com.neverland.thinkerbell.base.BaseFragment
import com.neverland.thinkerbell.custom.CustomLongDividerItemDecoration
import com.neverland.thinkerbell.databinding.FragmentHomeCalendarBinding
import com.neverland.thinkerbell.utils.UiState
import com.neverland.thinkerbell.view.OnRvItemClickListener
import com.neverland.thinkerbell.view.home.adapter.CalendarMonthAdapter
import com.neverland.thinkerbell.view.home.adapter.CalendarScheduleAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class HomeCalendarFragment : BaseFragment<FragmentHomeCalendarBinding>() {
    private val viewModel: HomeCalendarViewModel by viewModels()
    private lateinit var monthAdapter: CalendarMonthAdapter
    private lateinit var scheduleAdapter: CalendarScheduleAdapter
    private var currentYear = Calendar.getInstance().get(Calendar.YEAR) // 현재 연도를 저장
    private var currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1 // 현재 월을 저장 (1월이 1)

    override fun initView() {
        fetchSchedulesForCurrentMonth()  // 초기 데이터를 불러오기 위해 현재 연도와 월로 데이터 요청
        setCalendarRv()
        setScheduleRv()
    }

    override fun setObserver() {
        super.setObserver()

        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    // Handle loading state if necessary
                    binding.rvSchedule.visibility = View.VISIBLE
                }
                is UiState.Success -> {
                    // 성공 상태에서만 업데이트 수행
                    monthAdapter.setData(state.data)
                    updateSchedules(state.data)
                    binding.rvSchedule.visibility = View.VISIBLE
                }
                is UiState.Error -> {
                    // Handle error state if necessary
                    if (state.exception.message?.contains("현재 년도의 +-1의 년도 사이만 입력가능합니다.") == true) {
                        // 에러 메시지가 해당 조건에 맞으면 리사이클러뷰를 GONE 처리
                        binding.rvSchedule.visibility = View.GONE
                    }
                }
                UiState.Empty -> {
                    // Handle empty state if necessary
                }
            }
        }

        viewModel.toastState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Success -> {
                    showToast(state.data)
                }
                else -> {}
            }
        }
    }

    private fun fetchSchedulesForCurrentMonth() {
        viewModel.fetchData(currentYear, currentMonth) // 연도와 월을 같이 사용해 데이터 요청
    }

    private fun setScheduleRv() {
        binding.rvSchedule.layoutManager = LinearLayoutManager(context)
        scheduleAdapter = CalendarScheduleAdapter().apply {
            setRvItemClickListener(object : OnRvItemClickListener<Pair<Int, Boolean>> {
                override fun onClick(item: Pair<Int, Boolean>) {
                    if (item.second) viewModel.postBookmark(item.first) else viewModel.deleteBookmark(item.first)
                }
            })
        }
        binding.rvSchedule.adapter = scheduleAdapter

        binding.rvSchedule.addItemDecoration(CustomLongDividerItemDecoration(requireContext()))

        val position = Int.MAX_VALUE / 2
        updateSchedule(position)
    }

    private fun setCalendarRv() {
        monthAdapter = CalendarMonthAdapter(emptyList()) { newPosition ->
            binding.rvCalendar.scrollToPosition(newPosition)
            val calendar = monthAdapter.baseCalendar.clone() as Calendar
            calendar.add(Calendar.MONTH, newPosition - Int.MAX_VALUE / 2)

            val newMonth = calendar.get(Calendar.MONTH) + 1
            currentYear = calendar.get(Calendar.YEAR) // 새로운 연도 설정
            currentMonth = newMonth // 새로운 월 설정

            viewModel.fetchData(currentYear, currentMonth) // 연도와 월에 맞게 데이터를 갱신
        }

        binding.rvCalendar.layoutManager = object : LinearLayoutManager(context, HORIZONTAL, false) {
            override fun canScrollHorizontally(): Boolean = false
        }

        binding.rvCalendar.adapter = monthAdapter

        val position = Int.MAX_VALUE / 2
        binding.rvCalendar.scrollToPosition(position)

        val snap = PagerSnapHelper()
        snap.attachToRecyclerView(binding.rvCalendar)

        binding.rvCalendar.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val visiblePosition = layoutManager.findFirstCompletelyVisibleItemPosition()
                    if (visiblePosition != RecyclerView.NO_POSITION) {
                        updateSchedule(visiblePosition)
                    }
                }
            }
        })
    }

    private fun updateSchedules(schedules: List<AcademicSchedule>) {
        scheduleAdapter.updateSchedules(schedules)
    }

    private fun updateSchedule(position: Int) {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, position - Int.MAX_VALUE / 2)

        val newMonth = calendar.get(Calendar.MONTH) + 1
        currentYear = calendar.get(Calendar.YEAR) // 현재 연도 업데이트
        currentMonth = newMonth // 현재 월 업데이트

        val filteredSchedules = when (val state = viewModel.uiState.value) {
            is UiState.Success -> state.data.filter { schedule ->
                // 스케줄의 시작 날짜와 현재 연도, 월이 같은지 확인
                val scheduleYear = schedule.startDate.substring(0, 4).toInt()
                val scheduleMonth = schedule.startDate.substring(5, 7).toInt()
                scheduleYear == currentYear && scheduleMonth == currentMonth
            }
            else -> emptyList()
        }
        scheduleAdapter.updateSchedules(filteredSchedules)
    }
}
