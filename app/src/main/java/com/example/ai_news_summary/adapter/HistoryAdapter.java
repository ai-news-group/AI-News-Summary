package com.example.ai_news_summary.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ai_news_summary.R;
import com.example.ai_news_summary.core.model.History;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<History> historyList;
    private OnHistoryClickListener listener;
    private boolean isEditMode = false;
    private Set<Integer> selectedPositions = new HashSet<>();

    public interface OnHistoryClickListener {
        void onHistoryClick(History history);
        void onSelectionChanged(int selectedCount);
    }

    public HistoryAdapter(List<History> historyList, OnHistoryClickListener listener) {
        this.historyList = historyList;
        this.listener = listener;
    }

    public void setEditMode(boolean editMode) {
        if (this.isEditMode != editMode) {
            this.isEditMode = editMode;
            if (!editMode) {
                clearSelection();
            }
            notifyDataSetChanged();
        }
    }

    public boolean isEditMode() {
        return isEditMode;
    }

    public void clearSelection() {
        selectedPositions.clear();
        if (listener != null) {
            listener.onSelectionChanged(0);
        }
    }

    public Set<Integer> getSelectedPositions() {
        return selectedPositions;
    }

    public List<History> getSelectedHistories() {
        List<History> selected = new java.util.ArrayList<>();
        for (int position : selectedPositions) {
            if (position >= 0 && position < historyList.size()) {
                selected.add(historyList.get(position));
            }
        }
        return selected;
    }

    public void toggleSelection(int position) {
        if (selectedPositions.contains(position)) {
            selectedPositions.remove(position);
        } else {
            selectedPositions.add(position);
        }
        if (listener != null) {
            listener.onSelectionChanged(selectedPositions.size());
        }
        notifyItemChanged(position);
    }

    public void selectAll() {
        selectedPositions.clear();
        for (int i = 0; i < historyList.size(); i++) {
            selectedPositions.add(i);
        }
        if (listener != null) {
            listener.onSelectionChanged(selectedPositions.size());
        }
        notifyDataSetChanged();
    }

    public void updateList(List<History> newList) {
        this.historyList = newList;
        clearSelection();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        History history = historyList.get(position);

        // 添加日志
        android.util.Log.d("HistoryAdapter", "绑定第 " + position + " 条: " + history.getTitle());

        holder.tvTitle.setText(history.getTitle());
        holder.tvSummary.setText(history.getSummary());
        holder.tvSourceTag.setText(history.getSource());
        holder.tvReadTime.setText(history.getReadTime());

        // 编辑模式显示复选框
        if (isEditMode) {
            holder.cbSelect.setVisibility(View.VISIBLE);
            holder.cbSelect.setChecked(selectedPositions.contains(position));
        } else {
            holder.cbSelect.setVisibility(View.GONE);
        }

        // 点击事件 - 添加日志
        holder.itemView.setOnClickListener(v -> {
            android.util.Log.d("HistoryAdapter", "itemView 被点击了！position: " + position);
            if (isEditMode) {
                toggleSelection(position);
            } else {
                if (listener != null) {
                    android.util.Log.d("HistoryAdapter", "调用 listener.onHistoryClick");
                    listener.onHistoryClick(history);
                } else {
                    android.util.Log.d("HistoryAdapter", "listener 是 null！");
                }
            }
        });

        // 长按进入编辑模式
        holder.itemView.setOnLongClickListener(v -> {
            android.util.Log.d("HistoryAdapter", "itemView 被长按了！");
            if (!isEditMode) {
                setEditMode(true);
                toggleSelection(position);
            }
            return true;
        });

        // 复选框点击
        holder.cbSelect.setOnClickListener(v -> {
            android.util.Log.d("HistoryAdapter", "复选框被点击了");
            toggleSelection(position);
        });
    }

    @Override
    public int getItemCount() {
        return historyList == null ? 0 : historyList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox cbSelect;
        TextView tvTitle, tvSourceTag, tvSummary, tvReadTime;

        ViewHolder(View itemView) {
            super(itemView);
            cbSelect = itemView.findViewById(R.id.cb_select);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvSourceTag = itemView.findViewById(R.id.tv_source_tag);
            tvSummary = itemView.findViewById(R.id.tv_summary);
            tvReadTime = itemView.findViewById(R.id.tv_read_time);
        }
    }
}