package com.example.ai_news_summary.feature.recommend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ai_news_summary.databinding.FragmentRecommendBinding

class RecommendFragment : Fragment() {
    private var _binding: FragmentRecommendBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: RecommendViewModel
    private lateinit var adapter: RecommendAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecommendBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initViewModel()
        initListener()
        observeData()
    }

    private fun initRecyclerView() {
        adapter = RecommendAdapter { newsId, feedback ->
            viewModel.submitFeedback(newsId, feedback)
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[RecommendViewModel::class.java]
    }

    private fun initListener() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refreshRecommend()
        }
    }

    private fun observeData() {
        viewModel.recommendList.observe(viewLifecycleOwner) { list ->
            binding.swipeRefresh.isRefreshing = false
            if (list.isEmpty()) {
                binding.emptyState.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
            } else {
                binding.emptyState.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
                adapter.submitList(list)
            }
        }

        viewModel.feedbackSuccess.observe(viewLifecycleOwner) {
            Toast.makeText(context, "已记录你的偏好，推荐已更新", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}