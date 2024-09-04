package com.neverland.thinkerbell.view.myPage

import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.neverland.domain.model.keyword.Keyword
import com.neverland.thinkerbell.R
import com.neverland.thinkerbell.base.BaseFragment
import com.neverland.thinkerbell.databinding.FragmentKeywordManageBinding
import com.neverland.thinkerbell.utils.UiState
import com.neverland.thinkerbell.view.HomeActivity
import com.neverland.thinkerbell.view.OnRvItemClickListener
import com.neverland.thinkerbell.view.home.HomeFragment
import com.neverland.thinkerbell.view.myPage.adapter.KeywordManagementAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KeywordManageFragment :
    BaseFragment<FragmentKeywordManageBinding>() {

    private val myPageViewModel: MyPageViewModel by viewModels()
    private lateinit var keywordManagementAdapter: KeywordManagementAdapter
    override fun initView() {
        (requireActivity() as HomeActivity).apply {
            setStatusBarColor(R.color.primary1, true)
            hideBottomNavigation()
        }
    }

    override fun setObserver() {
        super.setObserver()

        myPageViewModel.keyword.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Loading -> {}
                is UiState.Success -> {
                    setupKeywordRecyclerView(it.data)
                    binding.tvKeywordCount.text = "${it.data.size} / 9"
                    updateAddBtnStyle(it.data.size)
                }

                is UiState.Error -> {}
                UiState.Empty -> {}
            }
        }
    }

    override fun onResume() {
        super.onResume()
        myPageViewModel.fetchKeyword()
    }

    override fun initListener() {
        super.initListener()
        binding.ivHomeLogo.setOnClickListener {
            (requireActivity() as HomeActivity).binding.bottomNavigation.selectedItemId = R.id.navigation_home
        }

        binding.btnKeywordAdd.setOnClickListener {
            (requireActivity() as HomeActivity).replaceFragment(
                R.id.fl_home,
                KeywordAddFragment(),
                true
            )
        }
    }

    private fun setupKeywordRecyclerView(list: List<Keyword>) {
        keywordManagementAdapter = KeywordManagementAdapter(list).apply {
            setOnRvItemClickListener(object : OnRvItemClickListener<String> {
                override fun onClick(item: String) {
                    KeywordDeleteDialog.newInstance(item) {
                        myPageViewModel.deleteKeyword(item)
                        keywordManagementAdapter.deleteKeyword(item)
                        binding.tvKeywordCount.text = "${keywordManagementAdapter.itemCount} / 9"
                        updateAddBtnStyle()
                    }.show(requireActivity().supportFragmentManager, "KeywordDeleteDialog")
                }
            })
        }

        binding.rvKeywordManagement.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = keywordManagementAdapter
        }
    }

    private fun updateAddBtnStyle(count: Int = keywordManagementAdapter.itemCount) {
        binding.tvEmptyView.visibility = if (count == 0) View.VISIBLE else View.GONE

        if (count >= 9) {
            binding.btnKeywordAdd.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.red_gray_100
                )
            )
            binding.btnKeywordAdd.background =
                ColorDrawable(ContextCompat.getColor(requireContext(), R.color.red_gray_300))
            binding.btnKeywordAdd.isClickable = false
        } else {
            binding.btnKeywordAdd.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.primary2
                )
            )
            binding.btnKeywordAdd.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.shape_keyword_add_button_bg)
            binding.btnKeywordAdd.isClickable = true
        }
    }
}