package com.example.ai_news_summary.feature.mine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ai_news_summary.databinding.FragmentMineBinding
import com.example.ai_news_summary.models.News

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
            News(1, "我的收藏1", "收藏的新闻描述1", "详细内容", "", "科技日报", "今天 10:00", "收藏"),
            News(2, "我的收藏2", "收藏的新闻描述2", "详细内容", "", "IT之家", "昨天 15:30", "收藏")
        )
        adapter.submitList(list)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _b = null
    }
}