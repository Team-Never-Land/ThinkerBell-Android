package com.neverland.thinkerbell.view

import android.view.View
import com.neverland.thinkerbell.R
import com.neverland.thinkerbell.base.BaseActivity
import com.neverland.thinkerbell.databinding.ActivityHomeBinding
import com.neverland.thinkerbell.view.category.CategoryFragment
import com.neverland.thinkerbell.view.home.HomeFragment
import com.neverland.thinkerbell.view.myPage.MyPageFragment
import com.neverland.thinkerbell.view.setting.SettingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    override fun initView() {
        showBottomNavigation()
        setStatusBarColor(R.color.primary1, true)
        replaceFragment(R.id.fl_home, HomeFragment(), false)
    }

    override fun initListener() {
        super.initListener()

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    replaceFragment(R.id.fl_home, HomeFragment(), false)
                    true
                }

                R.id.navigation_category -> {
                    replaceFragment(R.id.fl_home, CategoryFragment(), false)
                    true
                }

                R.id.navigation_my_page -> {
                    replaceFragment(R.id.fl_home, MyPageFragment(), false)
                    true
                }

                R.id.navigation_setting -> {
                    replaceFragment(R.id.fl_home, SettingFragment(), false)
                    true
                }

                else -> false
            }
        }

        binding.bottomNavigation.setOnItemReselectedListener { }
    }

    fun hideBottomNavigation() {
        binding.bottomNavigation.visibility = View.GONE
    }

    fun showBottomNavigation() {
        binding.bottomNavigation.visibility = View.VISIBLE
    }
}