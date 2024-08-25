package com.neverland.thinkerbell.view.alarm

import android.view.View
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.neverland.domain.model.keyword.Keyword
import com.neverland.thinkerbell.R
import com.neverland.thinkerbell.base.BaseFragment
import com.neverland.thinkerbell.databinding.FragmentAlarmBinding
import com.neverland.thinkerbell.utils.UiState
import com.neverland.thinkerbell.view.HomeActivity
import com.neverland.thinkerbell.view.alarm.adapter.AlarmVPAdapter
import com.neverland.thinkerbell.view.myPage.KeywordManageFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlarmFragment : BaseFragment<FragmentAlarmBinding>() {
    private val alarmViewModel: AlarmViewModel by viewModels()

    override fun initView() {
    }

    override fun setObserver() {
        super.setObserver()
        alarmViewModel.keywords.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    // Show loading state if needed
                }

                is UiState.Success -> {
                    state.data.forEach {
                        alarmViewModel.checkAlarm(Keyword(it.keyword))
                    }
                }

                is UiState.Error -> {
                    // Handle error state
                }

                UiState.Empty -> {
                    binding.groupEmpty.visibility = View.VISIBLE
                }
            }
        }

        alarmViewModel.checkAlarm.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Loading -> {
                    // Show loading state if needed
                }

                is UiState.Success -> {
                    alarmViewModel.addUnCheckedList(if (it.data.second) it.data.first else Keyword(""))
                    alarmViewModel.count++
                }

                is UiState.Error -> {
                    // Handle error state
                }

                UiState.Empty -> {

                }
            }
        }

        alarmViewModel.unCheckedList.observe(viewLifecycleOwner) {
            if (alarmViewModel.keywordSize <= alarmViewModel.count) {
                setupTabLayout(it.toList())
            }
        }

    }

    private fun setupTabLayout(unCheckedList: List<Keyword>) {
        val adapter = AlarmVPAdapter(this, alarmViewModel.keywordList)
        binding.vpAlarmNotice.adapter = adapter

        TabLayoutMediator(binding.tlAlarmCategoryTab, binding.vpAlarmNotice) { tab, position ->
            val tabView = layoutInflater.inflate(R.layout.tab_alarm_custum_view, null)
            val tabTitle = tabView.findViewById<TextView>(R.id.tab_title)
            val redDot = tabView.findViewById<View>(R.id.red_dot)

            tabTitle.text = alarmViewModel.keywordList[position].keyword

            // 알림이 있는 경우
            if (unCheckedList.contains(alarmViewModel.keywordList[position])) {
                redDot.visibility = View.VISIBLE
            } else {
                redDot.visibility = View.GONE
            }

            tab.customView = tabView
        }.attach()
    }

    override fun initListener() {
        super.initListener()
        binding.btnEmpty.setOnClickListener {
            (requireActivity() as HomeActivity).replaceFragment(
                R.id.fl_home,
                KeywordManageFragment(),
                true
            )
        }
    }

}