package com.neverland.thinkerbell.view.myPage

import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.neverland.core.utils.LoggerUtil
import com.neverland.domain.enums.NoticeType
import com.neverland.domain.model.univ.groupSchedulesByYearAndMonth
import com.neverland.thinkerbell.R
import com.neverland.thinkerbell.base.BaseFragment
import com.neverland.thinkerbell.databinding.FragmentFavoriteScheduleBinding
import com.neverland.thinkerbell.utils.UiState
import com.neverland.thinkerbell.view.HomeActivity
import com.neverland.thinkerbell.view.OnRvItemClickListener
import com.neverland.thinkerbell.view.myPage.adapter.FavoriteScheduleAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class FavoriteScheduleFragment : BaseFragment<FragmentFavoriteScheduleBinding>() {
    private val favoriteScheduleViewModel: FavoriteScheduleViewModel by viewModels()
    private lateinit var favoriteScheduleAdapter: FavoriteScheduleAdapter
    private var yearList: MutableSet<Int> = mutableSetOf()
    private var currentIndex: Int? = null
    override fun initView() {
        setupFavoriteSchedulesRecyclerView()
    }

    override fun setObserver() {
        super.setObserver()
        favoriteScheduleViewModel.schedules.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Loading -> {
                    // Handle loading state
                }

                is UiState.Success -> {
                    // 연도 리스트를 오름차순으로 정렬
                    yearList = groupSchedulesByYearAndMonth(it.data)
                        .map { scheduleGroup -> scheduleGroup.year }
                        .toMutableSet()
                        .sorted() // 오름차순으로 정렬
                        .toMutableSet()

                    groupSchedulesByYearAndMonth(it.data).forEach { yearList.add(it.year) }
                    val currentYear = Calendar.getInstance().get(Calendar.YEAR)

                    // yearList에서 현재 연도와 가장 가까운 연도를 찾는 함수
                    val closestYear = yearList.minByOrNull { year ->
                        if (year <= currentYear) currentYear - year // 현재 연도보다 작거나 같은 경우 (이전 연도 우선)
                        else year - currentYear // 현재 연도보다 큰 경우 (이후 연도)
                    }
                    // 가장 가까운 연도 또는 기본 연도를 텍스트에 설정
                    binding.tvYear.text = closestYear?.toString() ?: currentYear.toString()
                    currentIndex = yearList.indexOf(closestYear)
                    hideArrowButton()
                    favoriteScheduleAdapter.setList(it.data, binding.tvYear.text.toString().toInt())
                }

                is UiState.Error -> {
                    LoggerUtil.e(it.exception.message.toString())
                }

                UiState.Empty -> {

                }
            }
        }

        favoriteScheduleViewModel.bookmarkState.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Loading -> {}
                is UiState.Error -> {}
                is UiState.Empty -> {}
                is UiState.Success -> {
                    showToast(it.data)
                }
            }
        }

    }

    private fun setupFavoriteSchedulesRecyclerView() {
        // 어댑터 설정
        favoriteScheduleAdapter = FavoriteScheduleAdapter(scheduleGroups = emptyList())
        favoriteScheduleAdapter.setBookmarkClickListener(object :
            OnRvItemClickListener<Pair<Int, Boolean>> {
            override fun onClick(item: Pair<Int, Boolean>) {
                if (item.second) {
                    favoriteScheduleViewModel.postBookmark(NoticeType.ACADEMIC_SCHEDULE, item.first)
                } else {
                    favoriteScheduleViewModel.deleteBookmark(NoticeType.ACADEMIC_SCHEDULE, item.first)
                }
            }
        })
        binding.rvFavoriteSchedule.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = favoriteScheduleAdapter
        }
    }

    private fun hideArrowButton() {
        LoggerUtil.d("${yearList.size}, ${currentIndex}")
        // 현재 인덱스가 0일 경우 왼쪽 화살표 숨김, 아니면 보임
        if (currentIndex == 0) {
            binding.ibLeftArrow.visibility = View.GONE
        } else {
            binding.ibLeftArrow.visibility = View.VISIBLE
        }

        // 현재 인덱스가 마지막 인덱스일 경우 오른쪽 화살표 숨김, 아니면 보임
        if (currentIndex == yearList.size - 1) {
            binding.ibRightArrow.visibility = View.GONE
        } else {
            binding.ibRightArrow.visibility = View.VISIBLE
        }
    }

    override fun initListener() {
        super.initListener()
        binding.ivHomeLogo.setOnClickListener {
            (requireActivity() as HomeActivity).binding.bottomNavigation.selectedItemId = R.id.navigation_home
        }
        binding.ibLeftArrow.setOnClickListener {
            currentIndex = currentIndex!! - 1
            favoriteScheduleAdapter.setList(year = yearList.elementAt(currentIndex!!))
            binding.tvYear.text = yearList.elementAt(currentIndex!!).toString()
            hideArrowButton()
        }

        binding.ibRightArrow.setOnClickListener {
            currentIndex = currentIndex!! + 1
            favoriteScheduleAdapter.setList(year = yearList.elementAt(currentIndex!!))
            binding.tvYear.text = yearList.elementAt(currentIndex!!).toString()
            hideArrowButton()
        }
    }

}