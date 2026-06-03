package com.example.ai_news_summary.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ai_news_summary.R;
import com.example.ai_news_summary.core.model.History;
import java.util.ArrayList;
import java.util.List;

public class HistoryGroupAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private List<GroupItem> groupItems = new ArrayList<>();
    private OnHistoryClickListener listener;

    public interface OnHistoryClickListener {
        void onHistoryClick(History history);
    }

    public HistoryGroupAdapter(OnHistoryClickListener listener) {
        this.listener = listener;
    }

    public void setData(List<GroupItem> items) {
        this.groupItems = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        GroupItem item = groupItems.get(position);
        return item.isHeader() ? TYPE_HEADER : TYPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_HEADER) {
            View view = inflater.inflate(R.layout.item_history_group_header, parent, false);
            return new HeaderViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.item_history, parent, false);
            return new HistoryViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        GroupItem item = groupItems.get(position);
        if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).tvDate.setText(item.getDate());
        } else if (holder instanceof HistoryViewHolder) {
            History history = item.getHistory();
            ((HistoryViewHolder) holder).tvTitle.setText(history.getTitle());
            ((HistoryViewHolder) holder).tvSource.setText(history.getSource());
            ((HistoryViewHolder) holder).tvTime.setText(history.getReadTime());
            holder.itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onHistoryClick(history);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return groupItems.size();
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate;
        HeaderViewHolder(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tv_date_header);
        }
    }

    static class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvSource, tvTime;
        HistoryViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvSource = itemView.findViewById(R.id.tv_source);
            tvTime = itemView.findViewById(R.id.tv_time);
        }
    }

    public static class GroupItem {
        private String date;
        private List<History> histories;
        private boolean isHeader;

        public GroupItem(String date, List<History> histories) {
            this.date = date;
            this.histories = histories;
            this.isHeader = true;
        }

        public GroupItem(History history) {
            this.histories = new ArrayList<>();
            this.histories.add(history);
            this.isHeader = false;
        }

        public boolean isHeader() {
            return isHeader;
        }

        public String getDate() {
            return date;
        }

        public History getHistory() {
            return histories != null && !histories.isEmpty() ? histories.get(0) : null;
        }
    }
}