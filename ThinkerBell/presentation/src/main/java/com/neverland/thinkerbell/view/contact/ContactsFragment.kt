package com.neverland.thinkerbell.view.contact

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.neverland.domain.model.group.Group
import com.neverland.domain.model.univ.DeptContact
import com.neverland.thinkerbell.R
import com.neverland.thinkerbell.base.BaseFragment
import com.neverland.thinkerbell.custom.CustomDividerDecoration
import com.neverland.thinkerbell.databinding.FragmentContactsBinding
import com.neverland.thinkerbell.utils.UiState
import com.neverland.thinkerbell.view.HomeActivity
import com.neverland.thinkerbell.view.OnRvItemClickListener
import com.neverland.thinkerbell.view.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactsFragment : BaseFragment<FragmentContactsBinding>() {
    private val viewModel: ContactsViewModel by viewModels()
    private lateinit var expandableAdapter: ContactsExpandableAdapter

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

    private fun setRvAdapter(groups: List<Group<DeptContact>>) {
        expandableAdapter = ContactsExpandableAdapter(groups).apply {
            setOnRvItemClickListener(object : OnRvItemClickListener<DeptContact> {
                override fun onClick(item: DeptContact) {
                    ContactDialog.newInstance(item)
                        .show(requireActivity().supportFragmentManager, "")
                }
            })
        }
        binding.rvContacts.layoutManager = LinearLayoutManager(requireContext())
        binding.rvContacts.addItemDecoration(
            CustomDividerDecoration(
                requireContext(),
                "#404040",
                1f
            )
        )
        binding.rvContacts.adapter = expandableAdapter
    }

    override fun initListener() {
        super.initListener()
        binding.ivHomeLogo.setOnClickListener {
            (requireActivity() as HomeActivity).replaceFragment(R.id.fl_home, HomeFragment(), false)
        }
    }
}