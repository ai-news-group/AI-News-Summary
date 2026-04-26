package com.example.ai_news_summary.ui.favorite;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ai_news_summary.R;
import com.example.ai_news_summary.model.FavoriteItem;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.HashSet;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private List<FavoriteItem> items;
    private boolean isBatchMode = false;
    private OnItemClickListener listener;
    private OnItemSelectListener selectListener;
    private Set<String> selectedIds = new HashSet<>();

    public interface OnItemClickListener {
        void onItemClick(FavoriteItem item);
    }

    public interface OnItemSelectListener {
        void onSelectionChanged(int count);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnItemSelectListener(OnItemSelectListener listener) {
        this.selectListener = listener;
    }

    public void setItems(List<FavoriteItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void setBatchMode(boolean batchMode) {
        this.isBatchMode = batchMode;
        if (!batchMode) {
            selectedIds.clear();
            if (selectListener != null) selectListener.onSelectionChanged(0);
        }
        notifyDataSetChanged();
    }

    public void toggleSelection(String id) {
        if (selectedIds.contains(id)) {
            selectedIds.remove(id);
        } else {
            selectedIds.add(id);
        }
        if (selectListener != null) selectListener.onSelectionChanged(selectedIds.size());
        notifyDataSetChanged();
    }

    public boolean isSelected(String id) {
        return selectedIds.contains(id);
    }

    public Set<String> getSelectedIds() {
        return selectedIds;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_favorite, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FavoriteItem item = items.get(position);
        holder.bind(item, isBatchMode, selectedIds.contains(item.getId()), listener);
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvSummary, tvSource, tvTime;
        private View unreadDot;
        private CheckBox checkBox;
        private ImageView pinIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvSummary = itemView.findViewById(R.id.tv_summary);
            tvSource = itemView.findViewById(R.id.tv_source);
            tvTime = itemView.findViewById(R.id.tv_time);
            unreadDot = itemView.findViewById(R.id.unread_dot);
            checkBox = itemView.findViewById(R.id.checkbox);
            pinIcon = itemView.findViewById(R.id.pin_icon);
        }

        public void bind(FavoriteItem item, boolean isBatchMode, boolean isSelected,
                         OnItemClickListener listener) {
            tvTitle.setText(item.getTitle());
            tvSummary.setText(item.getSummary());
            tvSource.setText(item.getSource());

            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault());
            tvTime.setText(sdf.format(new Date(item.getTimestamp())));

            if (isBatchMode) {
                unreadDot.setVisibility(View.GONE);
                checkBox.setVisibility(View.VISIBLE);
                checkBox.setChecked(isSelected);
            } else {
                unreadDot.setVisibility(View.VISIBLE);
                if (item.isRead()) {
                    unreadDot.setBackgroundResource(R.drawable.dot_gray);
                } else {
                    unreadDot.setBackgroundResource(R.drawable.dot_blue);
                }
                checkBox.setVisibility(View.GONE);
            }

            if (item.isPinned()) {
                pinIcon.setVisibility(View.VISIBLE);
            } else {
                pinIcon.setVisibility(View.GONE);
            }

            itemView.setOnClickListener(v -> {
                if (!isBatchMode && listener != null) {
                    listener.onItemClick(item);
                }
            });

            if (isBatchMode) {
                checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    // Selection handled externally
                });
            }
        }
    }
}