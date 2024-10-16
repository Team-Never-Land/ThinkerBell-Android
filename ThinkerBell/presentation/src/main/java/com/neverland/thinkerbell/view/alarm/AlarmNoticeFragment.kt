package com.neverland.thinkerbell.view.alarm

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.neverland.core.utils.LoggerUtil
import com.neverland.domain.enums.NoticeType
import com.neverland.domain.model.alarm.Alarm
import com.neverland.thinkerbell.base.BaseFragment
import com.neverland.thinkerbell.custom.CustomDividerDecoration
import com.neverland.thinkerbell.databinding.FragmentAlarmNoticeBinding
import com.neverland.thinkerbell.utils.UiState
import com.neverland.thinkerbell.view.OnRvItemClickListener
import com.neverland.thinkerbell.view.alarm.adapter.AlarmNoticeAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlarmNoticeFragment : BaseFragment<FragmentAlarmNoticeBinding>() {
    private lateinit var noticeAdapter : AlarmNoticeAdapter
    private val alarmNoticeViewModel: AlarmNoticeViewModel by viewModels()

    companion object {
        private const val ARG_KEYWORD = "keyword"

        fun newInstance(keyword: String) = AlarmNoticeFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_KEYWORD, keyword)
            }
        }
    }

    private lateinit var keyword: String

    override fun initView() {
        LoggerUtil.d(arguments?.getString(ARG_KEYWORD) ?: "")
        noticeAdapter = AlarmNoticeAdapter().apply {
            setRvItemClickListener(object : OnRvItemClickListener<Pair<Int, String>> {
                override fun onClick(item: Pair<Int, String>) {
                    alarmNoticeViewModel.readAlarmNotice(item.first)
                    // 콜백 호출 후 읽지 않은 공지가 있는지 확인
                    val allRead = checkIfAllNoticesRead()
                    if (allRead) {
                        // 모든 공지를 읽었다면 뱃지 제거 로직 추가
                        removeBadgeForKeyword()
                    }
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse(item.second)
                    }
                    startActivity(intent)
                }
            })
            setBookmarkClickListener(object : OnRvItemClickListener<Pair<Alarm, Boolean>> {
                override fun onClick(item: Pair<Alarm, Boolean>) {
                    val noticeType = NoticeType.entries.find{
                        it.enName.lowercase() == item.first.noticeTypeEnglish.lowercase()
                    }?: NoticeType.NORMAL_NOTICE
                    LoggerUtil.d(item.first.noticeTypeEnglish)
                    if (item.second) {
                        alarmNoticeViewModel.postBookmark(noticeType, item.first.categoryId)
                    } else {
                        alarmNoticeViewModel.deleteBookmark(noticeType, item.first.categoryId)
                    }
                }
            })
        }
        binding.rvNoticeList.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(CustomDividerDecoration(requireContext(), "#404040", 1f ))
            adapter = noticeAdapter
        }
        alarmNoticeViewModel.fetchAlarmNotices(arguments?.getString(ARG_KEYWORD) ?: "")
    }

    override fun setObserver() {
        super.setObserver()
        alarmNoticeViewModel.alarmNotices.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    // Show loading state if needed
                }
                is UiState.Success -> {
                    if (state.data.isNotEmpty()) {
                        noticeAdapter.submitList(state.data)
                    }
                    else {
                        binding.tvEmptyAlarm.visibility = View.VISIBLE
                    }
                }
                is UiState.Error -> {
                    // Handle error state
                    LoggerUtil.e("$keyword ${state.exception.message}")
                }
                UiState.Empty -> {

                }
            }
        }

        alarmNoticeViewModel.readAlarm.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    // Show loading state if needed
                }
                is UiState.Success -> {

                }
                is UiState.Error -> {
                    // Handle error state
                }
                UiState.Empty -> {

                }
            }
        }

        alarmNoticeViewModel.bookmarkState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {}
                is UiState.Error -> {}
                is UiState.Empty -> {}
                is UiState.Success -> {
                    showToast(state.data)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        keyword = arguments?.getString(ARG_KEYWORD) ?: ""
    }

    private fun checkIfAllNoticesRead(): Boolean {
        // 공지 리스트에서 모두 읽음 여부를 확인
        val allNotices = noticeAdapter.currentList
        return allNotices.all { it.viewed }
    }
    private fun removeBadgeForKeyword() {
        // 모든 공지를 읽었을 때 뱃지를 제거하는 로직
        val parentFragment = parentFragment as? AlarmFragment
        parentFragment?.removeBadgeForKeyword(keyword)
    }
}
