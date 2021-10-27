package com.example.newsapp.room;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.R;

public class ArticleAdapterBookmark extends ListAdapter<ArticleTableBookmark, ArticleAdapterBookmark.ViewHolder> {
    private final ArticleBookmarkClicableCallback countrySeeLaterClicableCallback;

    public ArticleAdapterBookmark(@NonNull ArticleBookmarkClicableCallback countrySeeLaterClicableCallback) {
        super(new AsyncDifferConfig.Builder<>(new DiffUtil.ItemCallback<ArticleTableBookmark>() {
            @Override
            public boolean areItemsTheSame(@NonNull ArticleTableBookmark oldItem, @NonNull ArticleTableBookmark newItem) {
                return oldItem.uid == newItem.uid;
            }

            @Override
            public boolean areContentsTheSame(@NonNull ArticleTableBookmark oldItem, @NonNull ArticleTableBookmark newItem) {
                return oldItem.uid == newItem.uid;

            }
        }).build());
        this.countrySeeLaterClicableCallback = countrySeeLaterClicableCallback;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate( R.layout.news_rv_bookmark, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvName.setText(getItem(position).title);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.idTVNewsHeadingBM);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition(); // gets item position
            if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                ArticleTableBookmark countryTableSeeLater = getItem(position);
                countrySeeLaterClicableCallback.onClick(v, countryTableSeeLater);
            }
        }
    }

}
