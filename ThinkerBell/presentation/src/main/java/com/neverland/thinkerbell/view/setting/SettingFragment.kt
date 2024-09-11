package com.neverland.thinkerbell.view.setting

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.neverland.domain.model.keyword.Keyword
import com.neverland.thinkerbell.R
import com.neverland.thinkerbell.base.BaseFragment
import com.neverland.thinkerbell.databinding.FragmentSettingBinding
import com.neverland.thinkerbell.utils.UiState
import com.neverland.thinkerbell.view.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment: BaseFragment<FragmentSettingBinding>() {
    private val viewModel: SettingViewModel by viewModels()
    private lateinit var keywordAdapter : SettingKeywordAdapter

    override fun initView() {
        (requireActivity() as HomeActivity).apply {
            showBottomNavigation()
            setStatusBarColor(R.color.primary1, true)
        }

        viewModel.fetchKeyword()
    }

    override fun setObserver() {
        super.setObserver()

        viewModel.keyword.observe(viewLifecycleOwner){
            when(it){
                is UiState.Loading -> {}
                is UiState.Empty -> {}
                is UiState.Error -> {}
                is UiState.Success -> {
                    setupKeywordRecyclerView(it.data)
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

        with(binding){
            ibTos.setOnClickListener { openExternalBrowser("https://petite-pest-f69.notion.site/56313d788d914d6e8e996e099694272e") }
            ibPrivacyPolicy.setOnClickListener { openExternalBrowser("https://petite-pest-f69.notion.site/022b7a19351a418da5cf22304c7c3137") }
            ibKeyword.setOnClickListener { (requireActivity() as HomeActivity).replaceFragment(R.id.fl_home, KeywordManageFragment(), true) }
            ibError.setOnClickListener {  }
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