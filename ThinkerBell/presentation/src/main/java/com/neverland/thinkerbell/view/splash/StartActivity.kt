package com.neverland.thinkerbell.view.splash

import android.content.Intent
import androidx.activity.viewModels
import com.google.firebase.messaging.FirebaseMessaging
import com.neverland.thinkerbell.BuildConfig
import com.neverland.thinkerbell.R
import com.neverland.thinkerbell.base.BaseActivity
import com.neverland.thinkerbell.databinding.ActivityStartBinding
import com.neverland.thinkerbell.utils.UiState
import com.neverland.thinkerbell.view.HomeActivity
import com.neverland.thinkerbell.view.home.ForceUpdateDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartActivity : BaseActivity<ActivityStartBinding>() {
    private val viewModel: StartViewModel by viewModels()

    override fun initView() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            viewModel.saveDeviceInfo(task.result)
        }

        setStatusBarColor(R.color.primary2, false)
        viewModel.checkForceUpdate(BuildConfig.VERSION_CODE)
    }

    override fun setObserver() {
        super.setObserver()

        viewModel.update.observe(this) { state ->
            when(state){
                is UiState.Loading -> {}
                is UiState.Success -> {
                    if(state.data){
                        ForceUpdateDialog.newInstance().show(supportFragmentManager, "")
                    }
                }

                is UiState.Error -> {}
                is UiState.Empty -> {}
            }
        }

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