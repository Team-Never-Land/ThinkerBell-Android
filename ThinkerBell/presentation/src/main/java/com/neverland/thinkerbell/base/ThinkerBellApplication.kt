package com.neverland.thinkerbell.base

import android.annotation.SuppressLint
import android.app.Application
import android.provider.Settings
import com.neverland.core.utils.LoggerUtil
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ThinkerBellApplication: Application() {

    companion object {
        lateinit var application: ThinkerBellApplication
    }

    override fun onCreate() {
        super.onCreate()

        application = this
    }

    @SuppressLint("HardwareIds")
    fun getAndroidId(): String {
        val androidId = Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
        LoggerUtil.i("Android Id: $androidId")
        return androidId ?: ""
    }
}