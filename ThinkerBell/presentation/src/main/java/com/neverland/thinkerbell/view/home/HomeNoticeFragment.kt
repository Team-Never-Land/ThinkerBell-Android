package com.neverland.thinkerbell.view.home

import androidx.recyclerview.widget.LinearLayoutManager
import com.neverland.domain.model.notice.NoticeItem
import com.neverland.thinkerbell.base.BaseFragment
import com.neverland.thinkerbell.custom.CustomDividerDecoration
import com.neverland.thinkerbell.databinding.FragmentHomeNoticeBinding
import com.neverland.thinkerbell.view.home.adapter.HomeNoticeAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeNoticeFragment(
    private val notices: List<NoticeItem.CommonNotice>
) : BaseFragment<FragmentHomeNoticeBinding>() {

    override fun onResume() {
        super.onResume()

        binding.root.requestLayout()
    }

    override fun initView() {
        binding.rvHomeNotice.layoutManager = LinearLayoutManager(context)
        binding.rvHomeNotice.adapter = HomeNoticeAdapter(notices)

        val customDividerItemDecoration = CustomDividerDecoration(requireContext(), "#898989", 0.8f)
        binding.rvHomeNotice.addItemDecoration(customDividerItemDecoration)
    }
}