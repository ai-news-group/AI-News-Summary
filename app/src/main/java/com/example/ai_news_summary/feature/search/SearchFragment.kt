package com.example.ai_news_summary.feature.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ai_news_summary.core.model.News
import com.example.ai_news_summary.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {
    private var _b: FragmentSearchBinding? = null
    private val b get() = _b!!
    private val adapter = SearchNewsAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _b = FragmentSearchBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        b.recyclerView.layoutManager = LinearLayoutManager(context)
        b.recyclerView.adapter = adapter
        val list = listOf(
            News("搜索新闻1", "搜索新闻描述1", "2026-05-27"),
            News("搜索新闻2", "搜索新闻描述2", "2026-05-23")
        )
        adapter.submitList(list)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _b = null
    }
}