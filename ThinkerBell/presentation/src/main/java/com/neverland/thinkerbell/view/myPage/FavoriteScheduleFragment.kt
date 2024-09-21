package com.neverland.thinkerbell.view.myPage

import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.neverland.core.utils.LoggerUtil
import com.neverland.domain.enums.NoticeType
import com.neverland.domain.model.univ.AcademicSchedule
import com.neverland.domain.model.univ.BookmarkScheduleGroup
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
        LoggerUtil.d(currentIndex.toString())
        if (yearList.size-1 < currentIndex!!+1) {
            binding.ibRightArrow.visibility = View.GONE
        } else {
            binding.ibRightArrow.visibility = View.VISIBLE
        }
        if (yearList.size-1 > currentIndex!!-1) {
            binding.ibLeftArrow.visibility = View.GONE
        } else {
            binding.ibLeftArrow.visibility = View.VISIBLE
        }
    }

    override fun initListener() {
        super.initListener()
        binding.ivHomeLogo.setOnClickListener {
            (requireActivity() as HomeActivity).binding.bottomNavigation.selectedItemId = R.id.navigation_home
        }
        binding.ibLeftArrow.setOnClickListener {
            favoriteScheduleAdapter.setList(year = yearList.elementAt(currentIndex!!-1))
            binding.tvYear.text = yearList.elementAt(currentIndex!!-1).toString()
            currentIndex = currentIndex!!-1
            hideArrowButton()
        }
        binding.ibRightArrow.setOnClickListener {
            favoriteScheduleAdapter.setList(year = yearList.elementAt(currentIndex!!+1))
            binding.tvYear.text = yearList.elementAt(currentIndex!!+1).toString()
            currentIndex = currentIndex!!+1
            hideArrowButton()
        }
    }

}