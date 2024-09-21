package com.neverland.thinkerbell.view.myPage

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.neverland.domain.model.notice.RecentBookmarkNotice
import com.neverland.domain.model.univ.RecentBookmarkSchedule
import com.neverland.thinkerbell.R
import com.neverland.thinkerbell.base.BaseFragment
import com.neverland.thinkerbell.databinding.FragmentMyPageBinding
import com.neverland.thinkerbell.utils.UiState
import com.neverland.thinkerbell.view.HomeActivity
import com.neverland.thinkerbell.view.OnRvItemClickListener
import com.neverland.thinkerbell.view.myPage.adapter.MyPageFavoriteNoticeAdapter
import com.neverland.thinkerbell.view.myPage.adapter.MyPageFavoriteScheduleAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMyPageBinding>() {

    private val myPageviewModel: MyPageViewModel by viewModels()
    private lateinit var myPageFavoriteNoticeAdapter: MyPageFavoriteNoticeAdapter
    private lateinit var myPageFavoriteScheduleAdapter: MyPageFavoriteScheduleAdapter

    private var lastBackPressedTime: Long = 0
    private val onBackPressedCallback by lazy {
        object : OnBackPressedCallback(true) {
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
        setupStatusBarAndNavigation()
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)
    }

    override fun setObserver() {
        super.setObserver()

        observeUiState(
            myPageviewModel.recentFavoriteNotices,
            ::setupFavoriteNoticesRecyclerView,
            binding.rvMyPageFavoriteNotice,
            binding.ibPageRightNotices,
            binding.divider,
            binding.tvEmptyNoticeView
        )

        observeUiState(
            myPageviewModel.recentFavoriteSchedules,
            ::setupFavoriteSchedulesRecyclerView,
            binding.rvMyPageFavoriteSchedule,
            binding.ibPageRightSchedules,
            binding.divider2,
            binding.tvEmptyScheduleView
        )
    }

    override fun onResume() {
        super.onResume()
        myPageviewModel.fetchFavoriteNotices()
        myPageviewModel.fetchFavoriteSchedules()
    }

    private fun setupStatusBarAndNavigation() {
        (requireActivity() as HomeActivity).apply {
            setStatusBarColor(R.color.primary1, true)
            showBottomNavigation()
        }
    }

    private fun <T> observeUiState(
        uiState: LiveData<UiState<List<T>>>,
        setupRecyclerView: (List<T>) -> Unit,
        recyclerView: View,
        rightButton: View,
        divider: View,
        emptyView: View
    ) {
        uiState.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Loading -> {
                    // Handle loading state
                }
                is UiState.Success -> {
                    if (it.data.isNotEmpty()) {
                        setupRecyclerView(it.data)
                    } else {
                        rightButton.isClickable = false
                        rightButton.alpha = 0.5f
                        recyclerView.visibility = View.GONE
                        divider.visibility = View.GONE
                        emptyView.visibility = View.VISIBLE
                    }
                }
                is UiState.Error -> {
                    // Handle error state
                }
                UiState.Empty -> { }
            }
        }
    }

    private fun <T> setupRecyclerView(
        list: List<T>,
        adapter: RecyclerView.Adapter<*>,
        recyclerView: RecyclerView
    ) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            if (adapter is MyPageFavoriteScheduleAdapter) {
                myPageFavoriteScheduleAdapter = adapter.apply {
                    if (list.size >= 3) list.subList(0, 3) else list
                }
            } else if (adapter is MyPageFavoriteNoticeAdapter) {
                myPageFavoriteNoticeAdapter = adapter.apply {
                    if (list.size >= 3) list.subList(0, 3) else list
                }
            }
        }
    }

    private fun setupFavoriteNoticesRecyclerView(list: List<RecentBookmarkNotice>) {
        myPageFavoriteNoticeAdapter = MyPageFavoriteNoticeAdapter(if (list.size >= 3) list.subList(0, 3) else list).apply {
            setRvItemClickListener(object : OnRvItemClickListener<RecentBookmarkNotice> {
                override fun onClick(item: RecentBookmarkNotice) {
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse(item.url)
                    }
                    startActivity(intent)
                }
            })
        }
        binding.rvMyPageFavoriteNotice.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = myPageFavoriteNoticeAdapter
        }
    }

    private fun setupFavoriteSchedulesRecyclerView(list: List<RecentBookmarkSchedule>) {
        myPageFavoriteScheduleAdapter = MyPageFavoriteScheduleAdapter(if (list.size >= 3) list.subList(0, 3) else list)
        binding.rvMyPageFavoriteSchedule.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = myPageFavoriteScheduleAdapter
        }
    }

    override fun initListener() {
        super.initListener()

        binding.ivHomeLogo.setOnClickListener {
            (requireActivity() as HomeActivity).binding.bottomNavigation.selectedItemId = R.id.navigation_home
        }
        setupNavigationIconClickListener(binding.ibPageRightNotices, FavoriteNoticeFragment())
        setupNavigationIconClickListener(binding.ibPageRightSchedules, FavoriteScheduleFragment())
    }

    private fun setupNavigationIconClickListener(view: View, fragment: Fragment) {
        view.setOnClickListener {
            (requireActivity() as HomeActivity).replaceFragment(R.id.fl_home, fragment, true)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        onBackPressedCallback.remove()
    }
}
