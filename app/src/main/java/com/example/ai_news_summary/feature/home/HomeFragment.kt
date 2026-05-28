package com.example.ai_news_summary.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ai_news_summary.core.model.News
import com.example.ai_news_summary.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _b: FragmentHomeBinding? = null
    private val b get() = _b!!
    private val adapter = HomeAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _b = FragmentHomeBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        b.recyclerView.layoutManager = LinearLayoutManager(context)
        b.recyclerView.adapter = adapter

        val list = listOf(
            News("首页新闻1", "这是首页新闻描述1，点击可以查看详细内容", "今天 10:00"),
            News("首页新闻2", "这是首页新闻描述2，点击可以查看详细内容", "昨天 15:30"),
            News("首页新闻3", "这是首页新闻描述3，点击可以查看详细内容", "4月12日"),
            News("首页新闻4", "这是首页新闻描述4，点击可以查看详细内容", "4月11日")
        )
        list.forEach { it.source = "首页推荐" }
        adapter.submitList(list)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _b = null
    }
}