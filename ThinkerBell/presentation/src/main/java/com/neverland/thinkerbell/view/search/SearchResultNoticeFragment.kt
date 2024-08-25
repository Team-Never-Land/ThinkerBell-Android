package com.neverland.thinkerbell.view.search

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.neverland.domain.enums.NoticeType
import com.neverland.domain.model.notice.NoticeItem
import com.neverland.thinkerbell.base.BaseFragment
import com.neverland.thinkerbell.custom.CustomDividerDecoration
import com.neverland.thinkerbell.databinding.FragmentSearchResultNoticeBinding
import com.neverland.thinkerbell.utils.UiState
import com.neverland.thinkerbell.view.OnRvItemClickListener
import com.neverland.thinkerbell.view.search.adapter.SearchResultNoticeAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchResultNoticeFragment(
    private val noticeType: NoticeType,
    private val list: List<NoticeItem>
) : BaseFragment<FragmentSearchResultNoticeBinding>() {
    private val viewModel: SearchResultNoticeViewModel by viewModels()

    private val noticeAdapter by lazy {  SearchResultNoticeAdapter(noticeType).apply {
        setRvItemClickListener(object: OnRvItemClickListener<String> {
            override fun onClick(item: String) {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(item)
                }
                startActivity(intent)
            }
        })
        setBookmarkClickListener(object : OnRvItemClickListener<Pair<Int, Boolean>> {
            override fun onClick(item: Pair<Int, Boolean>) {
                if(item.second) viewModel.postBookmark(noticeType, item.first) else viewModel.deleteBookmark(noticeType, item.first)
            }
        })
    } }

    override fun initView() {
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.rvNoticeList.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(CustomDividerDecoration(requireContext(), "#404040", 1f ))
            adapter = noticeAdapter
        }

        noticeAdapter.submitList(list)
    }

    override fun setObserver() {
        super.setObserver()

        viewModel.toastState.observe(viewLifecycleOwner, ::handleToastState)
    }

    private fun handleToastState(state: UiState<String>){
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
