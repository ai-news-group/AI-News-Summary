package com.example.ai_news_summary.feature.recommend

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ai_news_summary.R
import com.example.ai_news_summary.databinding.ItemRecommendNewsBinding

class RecommendAdapter(
    private val onFeedbackClick: (newsId: Long, feedbackType: String) -> Unit
) : ListAdapter<News, RecommendAdapter.NewsViewHolder>(NewsDiffCallback()) {

    inner class NewsViewHolder(private val binding: ItemRecommendNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(news: News) {
            binding.tvTitle.text = news.title
            binding.tvSummary.text = news.summary
            binding.tvSource.text = "${news.source} · ${news.publishTime}"
            binding.tvRecommendReason.text = "根据你的阅读习惯推荐"

            binding.ivMore.setOnClickListener {
                val popup = PopupMenu(itemView.context, it)
                popup.menuInflater.inflate(R.menu.menu_recommend_feedback, popup.menu)
                popup.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.action_not_interested -> {
                            onFeedbackClick(news.id, "not_interested")
                            true
                        }
                        R.id.action_reduce_same -> {
                            onFeedbackClick(news.id, "reduce_same")
                            true
                        }
                        else -> false
                    }
                }
                popup.show()
            }
        }
    }

    class NewsDiffCallback : DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemRecommendNewsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}