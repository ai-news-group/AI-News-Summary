package com.example.ai_news_summary.feature.mine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ai_news_summary.core.model.News
import com.example.ai_news_summary.databinding.FragmentMineBinding

class MineFragment : Fragment() {
    private var _b: FragmentMineBinding? = null
    private val b get() = _b!!
    private val adapter = MineAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _b = FragmentMineBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        b.recyclerView.layoutManager = LinearLayoutManager(context)
        b.recyclerView.adapter = adapter

        val list = listOf(
            News("我的收藏1", "这是你收藏的新闻1", "2026-05-20"),
            News("我的收藏2", "这是你收藏的新闻2", "2026-05-19"),
            News("浏览历史1", "你浏览过的新闻1", "2026-05-18"),
            News("浏览历史2", "你浏览过的新闻2", "2026-05-17")
        )
        list.forEach { it.source = "我的" }
        adapter.submitList(list)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _b = null
    }
}