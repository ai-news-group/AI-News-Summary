package com.example.ai_news_summary.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ai_news_summary.databinding.FragmentHomeBinding
import com.example.ai_news_summary.models.News  // 改用 models.News

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

        // 使用 models.News 创建数据
        val list = listOf(
            News(1, "首页新闻1", "这是首页新闻描述1", "详细内容", "", "科技日报", "今天 10:00", "科技"),
            News(2, "首页新闻2", "这是首页新闻描述2", "详细内容", "", "IT之家", "昨天 15:30", "科技"),
            News(3, "首页新闻3", "这是首页新闻描述3", "详细内容", "", "通信世界", "4月12日", "科技")
        )
        adapter.submitList(list)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _b = null
    }
}