package com.neverland.thinkerbell.view.myPage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.neverland.domain.enums.NoticeType
import com.neverland.domain.model.notice.RecentBookmarkNotice
import com.neverland.thinkerbell.databinding.ItemMyNoticeBinding
import com.neverland.thinkerbell.view.OnRvItemClickListener

class MyPageFavoriteNoticeAdapter(private val notices: List<RecentBookmarkNotice>) :
    RecyclerView.Adapter<MyPageFavoriteNoticeAdapter.MyPageFavoriteNoticeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPageFavoriteNoticeViewHolder {
        val binding = ItemMyNoticeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyPageFavoriteNoticeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyPageFavoriteNoticeViewHolder, position: Int) {
        holder.bind(notices[position])
    }

    override fun getItemCount(): Int {
        return notices.size
    }

    inner class MyPageFavoriteNoticeViewHolder(private val binding: ItemMyNoticeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(notice: RecentBookmarkNotice) {
            val categoryKo = NoticeType.entries.find { it.enName == notice.category }?.koName?:""
            binding.tvNoticeTitle.text = "[${categoryKo}] ${notice.title}"
            binding.tvNoticeDate.text = notice.pubDate

            itemView.setOnClickListener {
                rvItemClickListener.onClick(notice)
            }
        }
    }

    private lateinit var rvItemClickListener: OnRvItemClickListener<RecentBookmarkNotice>

    fun setRvItemClickListener(rvItemClickListener: OnRvItemClickListener<RecentBookmarkNotice>){
        this.rvItemClickListener = rvItemClickListener
    }
}
