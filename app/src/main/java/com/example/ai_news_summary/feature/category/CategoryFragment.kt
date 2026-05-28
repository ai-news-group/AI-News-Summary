package com.example.ai_news_summary.feature.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ai_news_summary.core.model.News
import com.example.ai_news_summary.databinding.FragmentCategoryBinding

class CategoryFragment : Fragment() {
    private var _b: FragmentCategoryBinding? = null
    private val b get() = _b!!
    // 修改这里：使用 CategoryAdapter 而不是 CategoryNewsAdapter
    private val adapter = CategoryAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _b = FragmentCategoryBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        b.recyclerView.layoutManager = LinearLayoutManager(context)
        b.recyclerView.adapter = adapter

        val list = listOf(
            News("分类新闻1", "科技类新闻描述", "2026-05-27"),
            News("分类新闻2", "体育类新闻描述", "2026-05-26")
        )
        adapter.submitList(list)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _b = null
    }
}