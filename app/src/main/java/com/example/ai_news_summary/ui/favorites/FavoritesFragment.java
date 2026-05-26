package com.example.ai_news_summary.ui.favorites;

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
import java.util.List;

public class FavoritesFragment extends Fragment {

    private FavoritesViewModel viewModel;
    private RecyclerView recyclerView;
    private FavoritesAdapter adapter;
    private TextView tvEmpty;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(FavoritesViewModel.class);
        recyclerView = view.findViewById(R.id.recycler_favorites);
        tvEmpty = view.findViewById(R.id.tv_empty);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new FavoritesAdapter();
        recyclerView.setAdapter(adapter);

        viewModel.getFavoriteNews().observe(getViewLifecycleOwner(), newsList -> {
            if (newsList == null || newsList.isEmpty()) {
                recyclerView.setVisibility(View.GONE);
                tvEmpty.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setVisibility(View.VISIBLE);
                tvEmpty.setVisibility(View.GONE);
                adapter.setNewsList(newsList);
            }
        });
    }

    private class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

        private List<News> newsList = new java.util.ArrayList<>();

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
            holder.tvTime.setText(news.getTime());
            holder.tvSummary.setText(news.getSummary());
            holder.tvSource.setText(news.getSource());
            holder.ivFavorite.setVisibility(View.GONE);

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