package com.example.ai_news_summary.feature.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ai_news_summary.core.model.News
import com.example.ai_news_summary.databinding.ItemNewsBinding

class SearchNewsAdapter : ListAdapter<News, SearchNewsAdapter.VH>(Diff()) {
    inner class VH(val b: ItemNewsBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val b = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(b)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val news = getItem(position)
        holder.b.tvTitle.text = news.title
        holder.b.tvDesc.text = news.desc
        holder.b.tvTime.text = news.time
    }

    class Diff : DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean = oldItem.title == newItem.title
        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean = oldItem == newItem
    }
}