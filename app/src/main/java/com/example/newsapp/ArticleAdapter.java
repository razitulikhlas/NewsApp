package com.example.newsapp;

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

import com.example.newsapp.room.ArticleTable;

public class ArticleAdapter extends ListAdapter<ArticleTable, ArticleAdapter.ViewHolder> {
    private final ArticleClickableCallback articleClickableCallback;

    public ArticleAdapter(@NonNull ArticleClickableCallback articleClickableCallback) {
        super(new AsyncDifferConfig.Builder<>(new DiffUtil.ItemCallback<ArticleTable>() {
            @Override
            public boolean areItemsTheSame(@NonNull ArticleTable oldItem, @NonNull ArticleTable newItem) {
                return oldItem.uid == newItem.uid;
            }

            @Override
            public boolean areContentsTheSame(@NonNull ArticleTable oldItem, @NonNull ArticleTable newItem) {
                return oldItem.uid == newItem.uid;

            }
        }).build());
        this.articleClickableCallback = articleClickableCallback;

    }


    @NonNull
    @Override
    public ArticleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.news_rv_item, parent, false);
        ArticleAdapter.ViewHolder viewHolder = new ArticleAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleAdapter.ViewHolder holder, int position) {
        holder.tvName.setText(getItem(position).title);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.idTVNewsHeading);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition(); // gets item position
            if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                ArticleTable countryTable = getItem(position);
                articleClickableCallback.onClick(v, countryTable);
            }
        }
    }
}
