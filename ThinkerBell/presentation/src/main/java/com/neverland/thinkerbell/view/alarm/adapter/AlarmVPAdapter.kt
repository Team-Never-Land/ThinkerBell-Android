package com.neverland.thinkerbell.view.alarm.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.neverland.domain.model.keyword.Keyword
import com.neverland.thinkerbell.view.alarm.AlarmNoticeFragment

class AlarmVPAdapter(fragment: Fragment, private val keywords: List<Keyword>) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = keywords.size

    override fun createFragment(position: Int): Fragment {
        return AlarmNoticeFragment.newInstance(keywords[position].keyword)
    }
}
