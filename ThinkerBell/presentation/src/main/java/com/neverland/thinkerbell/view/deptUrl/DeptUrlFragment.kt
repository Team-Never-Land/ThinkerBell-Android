package com.neverland.thinkerbell.view.deptUrl

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.neverland.domain.model.group.Group
import com.neverland.domain.model.univ.DeptUrl
import com.neverland.thinkerbell.R
import com.neverland.thinkerbell.base.BaseFragment
import com.neverland.thinkerbell.custom.CustomDividerDecoration
import com.neverland.thinkerbell.databinding.FragmentDeptUrlBinding
import com.neverland.thinkerbell.utils.UiState
import com.neverland.thinkerbell.view.HomeActivity
import com.neverland.thinkerbell.view.OnRvItemClickListener
import com.neverland.thinkerbell.view.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeptUrlFragment : BaseFragment<FragmentDeptUrlBinding>() {
    private val viewModel: DeptUrlViewModel by viewModels()
    private lateinit var expandableAdapter: DeptUrlExpandableAdapter

    override fun initView() {
        (requireActivity() as HomeActivity).apply {
            showBottomNavigation()
            setStatusBarColor(R.color.primary1, true)
        }

        viewModel.fetchData()
    }

    override fun setObserver() {
        super.setObserver()

        viewModel.uiState.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Loading -> {}
                is UiState.Empty -> {}
                is UiState.Error -> {}
                is UiState.Success -> {
                    setRvAdapter(it.data)
                }
            }
        }
    }

    private fun setRvAdapter(groups: List<Group<DeptUrl>>) {
        expandableAdapter = DeptUrlExpandableAdapter(groups).apply {
            setOnRvItemClickListener(object : OnRvItemClickListener<String> {
                override fun onClick(item: String) {
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse(item)
                    }
                    startActivity(intent)
                }
            })
        }
        binding.rvDeptUrl.layoutManager = LinearLayoutManager(requireContext())
        binding.rvDeptUrl.addItemDecoration(
            CustomDividerDecoration(
                requireContext(),
                "#404040",
                1f
            )
        )
        binding.rvDeptUrl.adapter = expandableAdapter
    }

    override fun initListener() {
        super.initListener()
        binding.ivHomeLogo.setOnClickListener {
            (requireActivity() as HomeActivity).replaceFragment(R.id.fl_home, HomeFragment(), false)
        }
    }
}