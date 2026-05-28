package com.example.ai_news_summary.feature.recommend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ai_news_summary.core.model.News
import com.example.ai_news_summary.databinding.FragmentRecommendBinding

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
            News("AI技术最新突破", "人工智能在2026年取得重大进展，深度学习模型实现新突破，为各行各业带来革命性变化。", "今天 10:00"),
            News("Android 16发布", "谷歌正式发布Android 16，带来全新UI设计和性能优化，用户体验大幅提升。", "昨天 15:30"),
            News("5G应用全面普及", "5G技术在各个行业得到广泛应用，推动数字化转型和产业升级。", "4月12日"),
            News("智能家居新时代", "AI+物联网让家庭生活更加智能化、便捷化，智能家居市场迎来爆发。", "4月11日"),
            News("量子计算新进展", "中国科学家在量子计算领域取得重要突破，计算能力实现质的飞跃。", "4月10日"),
            News("新能源汽车销量创新高", "随着环保意识增强，新能源汽车销量持续攀升，市场渗透率突破40%。", "4月9日")
        )
        list.forEach { it.source = "推荐引擎" }
        adapter.submitList(list)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _b = null
    }
}