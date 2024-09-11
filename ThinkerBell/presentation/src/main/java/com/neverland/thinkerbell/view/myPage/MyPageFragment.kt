package com.neverland.thinkerbell.view.myPage

import android.content.Intent
import android.net.Uri
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.neverland.core.utils.LoggerUtil
import com.neverland.domain.model.keyword.Keyword
import com.neverland.domain.model.notice.RecentBookmarkNotice
import com.neverland.thinkerbell.R
import com.neverland.thinkerbell.base.BaseFragment
import com.neverland.thinkerbell.databinding.FragmentMyPageBinding
import com.neverland.thinkerbell.utils.UiState
import com.neverland.thinkerbell.view.HomeActivity
import com.neverland.thinkerbell.view.OnRvItemClickListener
import com.neverland.thinkerbell.view.home.HomeFragment
import com.neverland.thinkerbell.view.myPage.adapter.MyPageFavoriteNoticeAdapter
import com.neverland.thinkerbell.view.myPage.adapter.MyPageKeywordAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMyPageBinding>() {

    private val myPageviewModel: MyPageViewModel by viewModels()
    private lateinit var myPageFavoriteNoticeAdapter: MyPageFavoriteNoticeAdapter
    private lateinit var myPageKeywordAdapter: MyPageKeywordAdapter

    private var lastBackPressedTime: Long = 0
    private val onBackPressedCallback by lazy {
        object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                if (System.currentTimeMillis() - lastBackPressedTime < 2000) {
                    requireActivity().finish()
                } else {
                    lastBackPressedTime = System.currentTimeMillis()
                    showToast("한 번 더 누르면 종료됩니다.")
                }
            }
        }
    }

    override fun initView() {
        (requireActivity() as HomeActivity).apply {
            setStatusBarColor(R.color.primary1, true)
            showBottomNavigation()
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)
    }

    override fun setObserver() {
        super.setObserver()
        myPageviewModel.recentFavoriteNotices.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Loading -> {
                    // Handle loading state
                }

                is UiState.Success -> {
                    setupFavoriteNoticesRecyclerView(it.data)
                }

                is UiState.Error -> {
                    // Handle error state
                }

                UiState.Empty -> {

                }
            }
        }

        myPageviewModel.keyword.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Loading -> {
                    // Handle loading state
                }

                is UiState.Success -> {
                    setupKeywordRecyclerView(it.data)
                }

                is UiState.Error -> {
                    // Handle error state
                }

                UiState.Empty -> {

                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        myPageviewModel.fetchFavoriteNotices()
        myPageviewModel.fetchKeyword()
    }

    private fun setupKeywordRecyclerView(list: List<Keyword>) {
        myPageKeywordAdapter = MyPageKeywordAdapter(list)
        binding.rvMyPageKeyword.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = myPageKeywordAdapter
        }
    }

    private fun setupFavoriteNoticesRecyclerView(list: List<RecentBookmarkNotice>) {
        myPageFavoriteNoticeAdapter =
            MyPageFavoriteNoticeAdapter(if (list.size >= 3) list.subList(0, 3) else list).apply {
                setRvItemClickListener(object : OnRvItemClickListener<RecentBookmarkNotice> {
                    override fun onClick(item: RecentBookmarkNotice) {
                        val intent = Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse(item.url)
                        }
                        startActivity(intent)
                    }
                })
            }
        binding.rvMyPageFavorite.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = myPageFavoriteNoticeAdapter
        }
    }

    override fun initListener() {
        super.initListener()
        binding.ivHomeLogo.setOnClickListener {
            (requireActivity() as HomeActivity).binding.bottomNavigation.selectedItemId = R.id.navigation_home
        }
        binding.ibPageRightFavorite.setOnClickListener {
            (requireActivity() as HomeActivity).replaceFragment(
                R.id.fl_home,
                FavoriteFragment(),
                true
            )
        }
        binding.ibPageRightKeyword.setOnClickListener {
            (requireActivity() as HomeActivity).replaceFragment(
                R.id.fl_home,
                KeywordManageFragment(),
                true
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        onBackPressedCallback.remove()
    }
}