package com.example.ai_news_summary.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_source, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Source source = sourceList.get(position);
        holder.tvName.setText(source.getName());
        holder.tvCount.setText(source.getNewsCount() + "篇文章");

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onSourceClick(source);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sourceList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvCount;

        ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_source_name);
            tvCount = itemView.findViewById(R.id.tv_news_count);
        }
    }
}