package com.neverland.thinkerbell.view.splash

import android.content.Intent
import androidx.activity.viewModels
import com.google.firebase.messaging.FirebaseMessaging
import com.neverland.thinkerbell.R
import com.neverland.thinkerbell.base.BaseActivity
import com.neverland.thinkerbell.databinding.ActivityStartBinding
import com.neverland.thinkerbell.utils.UiState
import com.neverland.thinkerbell.view.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartActivity : BaseActivity<ActivityStartBinding>() {
    private val viewModel: StartViewModel by viewModels()

    override fun initView() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            viewModel.saveDeviceInfo(task.result)
        }

        setStatusBarColor(R.color.primary2, false)
    }

    override fun setObserver() {
        super.setObserver()

        viewModel.fcmState.observe(this) {
            when (it) {
                is UiState.Loading -> {}
                is UiState.Empty -> {}
                else -> {
                    moveHome()
                }
            }
        }
    }

    private fun moveHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}