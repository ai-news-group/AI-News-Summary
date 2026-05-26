package com.example.ai_news_summary.ui.favorite;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ai_news_summary.R;
import com.example.ai_news_summary.model.FolderItem;
import java.util.List;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.ViewHolder> {

    private List<FolderItem> folders;
    private OnFolderActionListener listener;

    public interface OnFolderActionListener {
        void onFolderClick(FolderItem folder);
        void onEditClick(FolderItem folder);
        void onDeleteClick(FolderItem folder);
    }

    public void setOnFolderActionListener(OnFolderActionListener listener) {
        this.listener = listener;
    }

    public void setFolders(List<FolderItem> folders) {
        this.folders = folders;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_folder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FolderItem folder = folders.get(position);
        holder.bind(folder, listener);
    }

    @Override
    public int getItemCount() {
        return folders == null ? 0 : folders.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvCount;
        private ImageButton btnEdit, btnDelete;
        private ImageView ivIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvCount = itemView.findViewById(R.id.tv_count);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            ivIcon = itemView.findViewById(R.id.iv_icon);
        }

        public void bind(FolderItem folder, OnFolderActionListener listener) {
            tvName.setText(folder.getName());
            tvCount.setText(folder.getItemCount() + " 条收藏");

            itemView.setOnClickListener(v -> {
                if (listener != null) listener.onFolderClick(folder);
            });

            btnEdit.setOnClickListener(v -> {
                if (listener != null) listener.onEditClick(folder);
            });

            btnDelete.setOnClickListener(v -> {
                if (listener != null) listener.onDeleteClick(folder);
            });
        }
    }
}
