package com.example.ai_news_summary.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ai_news_summary.R;
import com.example.ai_news_summary.core.model.News;  // ← 注意这个 import
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<News> newsList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(News news);
        void onFavoriteClick(News news, int position);
    }

    public NewsAdapter(List<News> newsList, OnItemClickListener listener) {
        this.newsList = newsList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        News news = newsList.get(position);
        holder.tvTitle.setText(news.getTitle());
        holder.tvSummary.setText(news.getSummary());
        holder.tvSource.setText(news.getSource());
        holder.tvTime.setText(news.getTime());

        if (news.isFavorite()) {
            holder.ivFavorite.setImageResource(R.drawable.ic_favorite);
        } else {
            holder.ivFavorite.setImageResource(R.drawable.ic_favorite_border);
        }

        holder.itemView.setOnClickListener(v -> listener.onItemClick(news));
        holder.ivFavorite.setOnClickListener(v -> listener.onFavoriteClick(news, position));
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvSummary, tvSource, tvTime;
        ImageView ivFavorite;

        ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvSummary = itemView.findViewById(R.id.tv_summary);
            tvSource = itemView.findViewById(R.id.tv_source);
            tvTime = itemView.findViewById(R.id.tv_time);
            ivFavorite = itemView.findViewById(R.id.iv_favorite);
        }
    }
}