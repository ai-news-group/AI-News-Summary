package com.example.ai_news_summary.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ai_news_summary.R;
import com.example.ai_news_summary.core.model.Source;
import java.util.List;

public class SourceAdapter extends RecyclerView.Adapter<SourceAdapter.ViewHolder> {

    private List<Source> sourceList;
    private OnSourceClickListener listener;

    public interface OnSourceClickListener {
        void onSourceClick(Source source);
    }

    public SourceAdapter(List<Source> sourceList, OnSourceClickListener listener) {
        this.sourceList = sourceList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_source, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Source source = sourceList.get(position);
        holder.tvSourceName.setText(source.getName());
        holder.tvNewsCount.setText(source.getNewsCount() + "篇文章");
        holder.tvSourceIcon.setText(source.getIcon());

        holder.itemView.setOnClickListener(v -> listener.onSourceClick(source));
    }

    @Override
    public int getItemCount() {
        return sourceList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvSourceIcon, tvSourceName, tvNewsCount;
        ViewHolder(View itemView) {
            super(itemView);
            tvSourceIcon = itemView.findViewById(R.id.tv_source_icon);
            tvSourceName = itemView.findViewById(R.id.tv_source_name);
            tvNewsCount = itemView.findViewById(R.id.tv_news_count);
        }
    }
}