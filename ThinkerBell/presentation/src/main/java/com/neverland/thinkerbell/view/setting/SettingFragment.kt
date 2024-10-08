package com.neverland.thinkerbell.view.setting

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.neverland.domain.model.keyword.Keyword
import com.neverland.thinkerbell.BuildConfig
import com.neverland.thinkerbell.R
import com.neverland.thinkerbell.base.BaseFragment
import com.neverland.thinkerbell.databinding.FragmentSettingBinding
import com.neverland.thinkerbell.utils.UiState
import com.neverland.thinkerbell.view.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>() {
    private val viewModel: SettingViewModel by viewModels()
    private lateinit var keywordAdapter: SettingKeywordAdapter

    override fun initView() {
        (requireActivity() as HomeActivity).apply {
            showBottomNavigation()
            setStatusBarColor(R.color.primary1, true)
        }

        setVersionName()
        viewModel.fetchAlarmStatus()
        viewModel.fetchKeyword()
    }

    private fun setVersionName() {
        val appUpdateManager = AppUpdateManagerFactory.create(requireContext())
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo

        appUpdateInfoTask.addOnCompleteListener { updateTask ->
            if (!updateTask.isSuccessful) {
                updateTask.exception?.printStackTrace()
                binding.tvVersionName.text = BuildConfig.VERSION_NAME

                return@addOnCompleteListener
            }

            val onMarketVersion = updateTask.result.availableVersionCode()
            if (onMarketVersion > BuildConfig.VERSION_CODE) {
                binding.tvVersionName.text = "업데이트"
                binding.tvVersionName.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse("https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}")
                        setPackage("com.android.vending")
                    }
                    try {
                        startActivity(intent)
                    } catch (e: ActivityNotFoundException) {
                        val webIntent = Intent(Intent.ACTION_VIEW).apply {
                            data =
                                Uri.parse("https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}")
                        }
                        startActivity(webIntent)
                    }
                }
            } else {
                binding.tvVersionName.text = BuildConfig.VERSION_NAME
            }
        }
    }

    override fun setObserver() {
        super.setObserver()

        viewModel.keyword.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Loading -> {}
                is UiState.Empty -> {}
                is UiState.Error -> {}
                is UiState.Success -> {
                    setupKeywordRecyclerView(it.data)
                }
            }
        }

        viewModel.uiState.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Loading -> {}
                is UiState.Empty -> {}
                is UiState.Error -> {}
                is UiState.Success -> {
                    binding.switchAlarm.isChecked = it.data

                    binding.switchAlarm.setOnCheckedChangeListener { _, _ -> viewModel.patchAlarmStatus() }
                }
            }
        }

        viewModel.alarmStatus.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Loading -> {}
                is UiState.Empty -> {}
                is UiState.Error -> {}
                is UiState.Success -> {
                    binding.switchAlarm.isChecked = !binding.switchAlarm.isChecked
                }
            }
        }
    }

    private fun setupKeywordRecyclerView(list: List<Keyword>) {
        keywordAdapter = SettingKeywordAdapter(list)
        binding.rvSettingKeyword.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = keywordAdapter
        }
    }

    override fun initListener() {
        super.initListener()

        with(binding) {
            ibTos.setOnClickListener { openExternalBrowser("https://petite-pest-f69.notion.site/56313d788d914d6e8e996e099694272e") }
            ibPrivacyPolicy.setOnClickListener { openExternalBrowser("https://petite-pest-f69.notion.site/022b7a19351a418da5cf22304c7c3137") }
            ibKeyword.setOnClickListener {
                (requireActivity() as HomeActivity).replaceFragment(
                    R.id.fl_home,
                    KeywordManageFragment(),
                    true
                )
            }
            ibError.setOnClickListener {
                (requireActivity() as HomeActivity).replaceFragment(
                    R.id.fl_home,
                    ErrorReportFragment(),
                    true
                )
            }
        }
    }

    private fun openExternalBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }

        startActivity(intent)
    }
}