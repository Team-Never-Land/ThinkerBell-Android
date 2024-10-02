package com.neverland.thinkerbell.view.setting

import android.graphics.drawable.ColorDrawable
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.neverland.thinkerbell.R
import com.neverland.thinkerbell.base.BaseFragment
import com.neverland.thinkerbell.databinding.FragmentErrorReportBinding
import com.neverland.thinkerbell.utils.UiState
import com.neverland.thinkerbell.view.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ErrorReportFragment : BaseFragment<FragmentErrorReportBinding>() {

    private val viewModel: ErrorReportViewModel by viewModels()

    override fun initView() {
        (requireActivity() as HomeActivity).apply {
            hideBottomNavigation()
            setStatusBarColor(R.color.primary1, true)
        }
        binding.etReport.setRawInputType(InputType.TYPE_CLASS_TEXT)
    }

    override fun setObserver() {
        super.setObserver()

        viewModel.uiState.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Loading -> {}
                is UiState.Empty -> {}
                is UiState.Error -> {
                    showToast(it.exception.message ?: "Unknown error")
                }

                is UiState.Success -> {
                    showToast(it.data)
                    requireActivity().supportFragmentManager.popBackStack()
                }
            }
        }
    }

    override fun initListener() {
        super.initListener()

        binding.root.setOnClickListener {
            hideKeyboard()
            binding.etReport.clearFocus()
        }
        binding.btnBack.setOnClickListener { requireActivity().supportFragmentManager.popBackStack() }

        binding.etReport.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                hideKeyboard()
                binding.etReport.clearFocus()
                true
            } else {
                false
            }
        }

        binding.etReport.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val len = s?.length ?: 0
                if (len >= 10) {
                    binding.tvReportComment.visibility = View.GONE
                }
            }
        })

        binding.btnSendReport.setOnClickListener {
            if(binding.etReport.text.length < 10) {
                binding.tvReportComment.visibility = View.VISIBLE
            } else {
                viewModel.postErrorReport(binding.etReport.text.toString())
            }
        }
    }
}