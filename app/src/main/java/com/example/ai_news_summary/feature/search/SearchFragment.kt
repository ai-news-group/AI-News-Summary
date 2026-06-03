package com.example.ai_news_summary.feature.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ai_news_summary.R
import com.example.ai_news_summary.models.News

class SearchFragment : Fragment() {

    private lateinit var etSearchInput: EditText
    private lateinit var btnSearch: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var tvEmpty: TextView
    private lateinit var adapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 初始化控件
        etSearchInput = view.findViewById(R.id.et_search_input)
        btnSearch = view.findViewById(R.id.btn_search)
        recyclerView = view.findViewById(R.id.recyclerView)
        tvEmpty = view.findViewById(R.id.tv_empty)

        // 设置 RecyclerView
        adapter = SearchAdapter()
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        // 设置搜索按钮点击事件
        btnSearch.setOnClickListener {
            performSearch()
        }
    }

    private fun performSearch() {
        val keyword = etSearchInput.text.toString().trim()

        if (keyword.isEmpty()) {
            tvEmpty.visibility = View.VISIBLE
            tvEmpty.text = "请输入搜索关键词"
            recyclerView.visibility = View.GONE
            return
        }

        // 模拟搜索结果
        val results = mutableListOf<News>()

        // 添加一些模拟数据
        if (keyword.contains("AI") || keyword.contains("人工智能")) {
            results.add(News(1, "AI技术最新进展", "人工智能在2026年取得了重大突破...", "详细内容", "", "科技日报", "今天 10:00", "科技"))
            results.add(News(2, "机器学习新算法", "新的机器学习算法提高了训练效率...", "详细内容", "", "科技日报", "昨天 15:30", "科技"))
        }

        if (keyword.contains("Android")) {
            results.add(News(3, "Android 16发布", "谷歌发布了Android 16，带来全新特性...", "详细内容", "", "IT之家", "昨天 09:00", "科技"))
        }

        if (results.isEmpty()) {
            tvEmpty.visibility = View.VISIBLE
            tvEmpty.text = "没有找到相关新闻"
            recyclerView.visibility = View.GONE
        } else {
            tvEmpty.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
            adapter.submitList(results)
        }
    }
}