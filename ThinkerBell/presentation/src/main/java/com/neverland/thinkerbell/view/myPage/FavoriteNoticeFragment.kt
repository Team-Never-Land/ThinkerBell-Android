package com.neverland.thinkerbell.view.myPage

import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.neverland.domain.enums.NoticeType
import com.neverland.domain.model.notice.NoticeItem
import com.neverland.thinkerbell.R
import com.neverland.thinkerbell.base.BaseFragment
import com.neverland.thinkerbell.databinding.FragmentFavoriteNoticeBinding
import com.neverland.thinkerbell.utils.UiState
import com.neverland.thinkerbell.view.HomeActivity
import com.neverland.thinkerbell.view.myPage.adapter.FavoriteNoticeVPAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteNoticeFragment : BaseFragment<FragmentFavoriteNoticeBinding>() {
    private val favoriteNoticeViewModel: FavoriteNoticeViewModel by viewModels()
    override fun initView() {

    }

    override fun setObserver() {
        super.setObserver()
        favoriteNoticeViewModel.notices.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Loading -> {
                    // Handle loading state
                }

                is UiState.Success -> {
                    setupTabLayout(it.data)
                }

                is UiState.Error -> {
                    // Handle error state
                }

                UiState.Empty -> {

                }
            }
        }
    }

    private fun setupTabLayout(category: Map<NoticeType, List<NoticeItem>>) {
        val keys = category.keys.toList()
        val adapter = FavoriteNoticeVPAdapter(this, category)
        binding.vpFavoriteNotice.adapter = adapter

        TabLayoutMediator(
            binding.tlFavoriteCategoryTab,
            binding.vpFavoriteNotice
        ) { tab, position ->
            tab.text = keys[position].tabName
        }.attach()
    }

    override fun initListener() {
        super.initListener()
        binding.ivHomeLogo.setOnClickListener {
            (requireActivity() as HomeActivity).binding.bottomNavigation.selectedItemId = R.id.navigation_home
        }
    }
}
