package com.neverland.thinkerbell.view.myPage.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.neverland.domain.enums.NoticeType
import com.neverland.domain.model.notice.NoticeItem
import com.neverland.thinkerbell.view.myPage.FavoriteNoticeListFragment

class FavoriteNoticeVPAdapter(fragment: Fragment, private val category: Map<NoticeType, List<NoticeItem>>) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = category.size

    override fun createFragment(position: Int): Fragment {
        val key = category.keys.toList()[position]
        return FavoriteNoticeListFragment(category[key] ?: emptyList(), NoticeType.entries.find { it.enName==key.enName }!!)
    }

}