package com.neverland.thinkerbell.view.setting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.neverland.domain.model.keyword.Keyword
import com.neverland.thinkerbell.databinding.ItemMyPageKeywordBinding
import com.neverland.thinkerbell.utils.DisplayUtils

class SettingKeywordAdapter(
    private val keywords: List<Keyword>
) : RecyclerView.Adapter<SettingKeywordAdapter.KeywordViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeywordViewHolder {
        val binding = ItemMyPageKeywordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return KeywordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: KeywordViewHolder, position: Int) {
        val layoutParams = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
        if (position == 0) {
            layoutParams.leftMargin = DisplayUtils.dpToPx(holder.itemView.context, 24f).toInt()
        } else {
            layoutParams.leftMargin = 0
        }
        holder.itemView.layoutParams = layoutParams

        holder.bind(keywords[position])
    }

    override fun getItemCount(): Int = keywords.size

    class KeywordViewHolder(private val binding: ItemMyPageKeywordBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(keyword: Keyword) {
            binding.tvKeyword.text = keyword.keyword
        }
    }
}
