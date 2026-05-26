package com.example.ai_news_summary.ui.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ai_news_summary.R;
import com.example.ai_news_summary.core.model.News;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HistoryFragment extends Fragment {

    private HistoryViewModel viewModel;
    private RecyclerView recyclerView;
    private HistoryAdapter adapter;
    private TextView tvEmpty;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(HistoryViewModel.class);
        recyclerView = view.findViewById(R.id.recycler_history);
        tvEmpty = view.findViewById(R.id.tv_empty);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new HistoryAdapter();
        recyclerView.setAdapter(adapter);

        viewModel.getHistoryNews().observe(getViewLifecycleOwner(), newsList -> {
            if (newsList == null || newsList.isEmpty()) {
                recyclerView.setVisibility(View.GONE);
                tvEmpty.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setVisibility(View.VISIBLE);
                tvEmpty.setVisibility(View.GONE);
                adapter.setNewsList(newsList);
            }
        });

        view.findViewById(R.id.btn_clear_history).setOnClickListener(v -> {
            viewModel.clearHistory();
            Toast.makeText(getContext(), "历史已清空", Toast.LENGTH_SHORT).show();
        });
    }

    private class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

        private List<News> newsList = new java.util.ArrayList<>();
        private List<Long> readTimes = new java.util.ArrayList<>();

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.items_news, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            News news = newsList.get(position);
            holder.tvTitle.setText(news.getTitle());
            holder.tvSummary.setText(news.getSummary());
            holder.tvSource.setText(news.getSource());
            holder.ivFavorite.setVisibility(View.GONE);

            if (readTimes.size() > position) {
                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault());
                holder.tvTime.setText("浏览于：" + sdf.format(new Date(readTimes.get(position))));
            } else {
                holder.tvTime.setText(news.getTime());
            }

            holder.itemView.setOnClickListener(v -> {
                Toast.makeText(getContext(), "打开新闻：" + news.getTitle(), Toast.LENGTH_SHORT).show();
            });
        }

        @Override
        public int getItemCount() {
            return newsList.size();
        }

        public void setNewsList(List<News> list) {
            this.newsList = list;
            notifyDataSetChanged();
        }

        public void setReadTimes(List<Long> times) {
            this.readTimes = times;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvTitle, tvTime, tvSummary, tvSource;
            ImageView ivFavorite;

            ViewHolder(View itemView) {
                super(itemView);
                tvTitle = itemView.findViewById(R.id.tv_title);
                tvTime = itemView.findViewById(R.id.tv_time);
                tvSummary = itemView.findViewById(R.id.tv_summary);
                tvSource = itemView.findViewById(R.id.tv_source);
                ivFavorite = itemView.findViewById(R.id.iv_favorite);
            }
        }
    }
}