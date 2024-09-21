package com.neverland.thinkerbell.view.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.neverland.core.utils.LoggerUtil
import com.neverland.domain.model.notice.RecentNotices
import com.neverland.domain.model.univ.Banner
import com.neverland.thinkerbell.R
import com.neverland.thinkerbell.base.BaseFragment
import com.neverland.thinkerbell.databinding.FragmentHomeBinding
import com.neverland.thinkerbell.utils.UiState
import com.neverland.thinkerbell.view.HomeActivity
import com.neverland.thinkerbell.view.alarm.AlarmFragment
import com.neverland.thinkerbell.view.contact.ContactsFragment
import com.neverland.thinkerbell.view.deptUrl.DeptUrlFragment
import com.neverland.thinkerbell.view.home.adapter.HomeBannerAdapter
import com.neverland.thinkerbell.view.home.adapter.HomeCategoryVPAdapter
import com.neverland.thinkerbell.view.search.SearchFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var bannerAdapter: HomeBannerAdapter

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
        (requireActivity() as HomeActivity).apply {
            showBottomNavigation()
            setStatusBarColor(R.color.primary1, true)
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressedCallback
        )
        askNotificationPermission()
        viewModel.fetchBanners()
        viewModel.fetchRecentNotices()
        viewModel.checkAllAlarm()
    }

    override fun setObserver() {
        super.setObserver()

        viewModel.banners.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {}
                is UiState.Success -> {
                    setBanner(state.data)
                }

                is UiState.Error -> {
                    showToast("배너 조회 실패")
                }

                is UiState.Empty -> {}
            }
        }

        viewModel.uiState.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Loading -> {}
                is UiState.Success -> {
                    setHomeNoticeRv(it.data)
                }

                is UiState.Error -> {
                    showToast("최근 공지 조회 실패")
                }

                is UiState.Empty -> {}
            }
        }

        viewModel.alarmState.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Loading -> {}
                is UiState.Success -> {
                    binding.ivHomeNotificationIcon.setImageResource(if (it.data) R.drawable.ic_topbar_bell_badge else R.drawable.ic_topbar_bell)
                }
                is UiState.Error -> {}
                is UiState.Empty -> {}
            }
        }
    }

    private fun setBanner(banners: List<Banner>) {
        // 배너 설정
        bannerAdapter = HomeBannerAdapter(banners)
        binding.vpHomeBanner.adapter = bannerAdapter

        // 페이지 인디케이터 설정
        TabLayoutMediator(
            binding.tlHomeBannerIndicator,
            binding.vpHomeBanner
        ) { _, _ -> }.attach()

        // 배너 자동 슬라이드
        autoSlideBanner()
    }

    private fun setHomeNoticeRv(notices: RecentNotices) {
        val categories = resources.getStringArray(R.array.category_list)

        // ViewPager2에 어댑터 설정
        val adapter = HomeCategoryVPAdapter(requireActivity(), notices)
        binding.vpHomeNotice.adapter = adapter

        // TabLayout과 ViewPager2를 연결
        TabLayoutMediator(binding.tlHomeCategoryTab, binding.vpHomeNotice) { tab, position ->
            tab.text = categories[position]
        }.attach()
    }

    override fun initListener() {
        super.initListener()
        // 알림 버튼 클릭 시
        binding.ivHomeNotificationIcon.setOnClickListener {
            (requireActivity() as HomeActivity).replaceFragment(R.id.fl_home, AlarmFragment(), true)
        }
        // 검색 버튼 클릭 시
        binding.ivHomeSearchIcon.setOnClickListener {
            (requireActivity() as HomeActivity).replaceFragment(
                R.id.fl_home,
                SearchFragment(),
                true
            )
        }

        binding.btnHomeDeptPhone.setOnClickListener {
            (requireActivity() as HomeActivity).replaceFragment(
                R.id.fl_home,
                ContactsFragment(),
                true
            )
        }

        binding.btnHomeDeptHomepage.setOnClickListener {
            (requireActivity() as HomeActivity).replaceFragment(
                R.id.fl_home,
                DeptUrlFragment(),
                true
            )
        }

        binding.ibHomeCategory.setOnClickListener {
            (requireActivity() as HomeActivity).binding.bottomNavigation.selectedItemId =
                R.id.navigation_category
        }
    }

    private fun autoSlideBanner() {
        val job = CoroutineScope(Dispatchers.Main).launch {
            while (isActive) {
                val currentItem = binding.vpHomeBanner.currentItem
                val nextItem =
                    if (currentItem == bannerAdapter.itemCount - 1) 0 else currentItem + 1
                binding.vpHomeBanner.setCurrentItem(nextItem, true)
                delay(4000)
            }
        }

        binding.vpHomeBanner.addOnAttachStateChangeListener(object :
            View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {}
            override fun onViewDetachedFromWindow(v: View) {
                job.cancel()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onBackPressedCallback.remove()
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            showToast("알림 권한이 허용되었습니다.")
        } else {
            showToast("알림 권한이 거부되었습니다.\n설정에서 권한을 허용해주세요.")
        }
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                LoggerUtil.d("POST_NOTIFICATION is Granted.")
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                showPermissionRationale()
            } else {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun showPermissionRationale() {
        AlertDialog.Builder(requireContext())
            .setTitle("알림 권한 필요")
            .setMessage("알림 기능을 사용하려면 권한이 필요합니다.")
            .setPositiveButton("권한 요청") { _, _ ->
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
            .setNegativeButton("취소", null)
            .show()
    }
}
