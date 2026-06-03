package com.example.ai_news_summary.feature.search

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ai_news_summary.NewsDetailActivity
import com.example.ai_news_summary.databinding.ItemSearchNewsBinding
import com.example.ai_news_summary.models.News

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    private var newsList = listOf<News>()

    class ViewHolder(private val binding: ItemSearchNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(news: News) {
            binding.tvTitle.text = news.title
            binding.tvDesc.text = news.description
            binding.tvTime.text = news.date

            binding.root.setOnClickListener {
                val context = binding.root.context
                val intent = Intent(context, NewsDetailActivity::class.java)
                intent.putExtra("news", news)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSearchNewsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(newsList[position])
    }

    override fun getItemCount(): Int = newsList.size

    fun submitList(list: List<News>) {
        this.newsList = list
        notifyDataSetChanged()
    }
}