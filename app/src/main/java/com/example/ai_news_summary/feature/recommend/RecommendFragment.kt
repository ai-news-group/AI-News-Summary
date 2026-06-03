package com.example.ai_news_summary.feature.recommend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ai_news_summary.databinding.FragmentRecommendBinding
import com.example.ai_news_summary.models.News  // 改用 models.News

class RecommendFragment : Fragment() {
    private var _b: FragmentRecommendBinding? = null
    private val b get() = _b!!
    private val adapter = RecommendAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _b = FragmentRecommendBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        b.recyclerView.layoutManager = LinearLayoutManager(context)
        b.recyclerView.adapter = adapter

        val list = listOf(
            News(1, "推荐新闻1", "AI推荐内容描述1", "详细内容", "", "推荐引擎", "今天 10:00", "推荐"),
            News(2, "推荐新闻2", "AI推荐内容描述2", "详细内容", "", "推荐引擎", "昨天 15:30", "推荐"),
            News(3, "推荐新闻3", "AI推荐内容描述3", "详细内容", "", "推荐引擎", "4月12日", "推荐")
        )
        adapter.submitList(list)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _b = null
    }
}