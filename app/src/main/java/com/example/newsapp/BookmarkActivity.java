package com.example.newsapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.room.ArticleAdapterBookmark;
import com.example.newsapp.room.ArticleBookmarkClicableCallback;
import com.example.newsapp.room.ArticleRoomViewModel;
import com.example.newsapp.room.ArticleTableBookmark;
import com.example.newsapp.room.BookmarkViewSaveDialogFragment;

public class BookmarkActivity extends SessionActivity{
    private ArticleRoomViewModel articleRoomViewModel;
    private RecyclerView recyclerView;
    private ArticleAdapterBookmark articleAdapterBookmark;

    private final ArticleBookmarkClicableCallback articleBookmarkClicableCallback = new ArticleBookmarkClicableCallback() {
        @Override
        public void onClick(View view, ArticleTableBookmark articleTableBookmark) {
            DialogFragment dialogFragment = BookmarkViewSaveDialogFragment.newInstance(articleTableBookmark);
            dialogFragment.show(getSupportFragmentManager(), "SeeLaterViewSaveDialogFragment");
        }

    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        articleRoomViewModel = new ViewModelProvider(BookmarkActivity.this).get(ArticleRoomViewModel.class);

        recyclerView = (RecyclerView) findViewById(R.id.roomRecyclerViewSeeLater);
        articleAdapterBookmark = new ArticleAdapterBookmark(articleBookmarkClicableCallback);
        recyclerView.setAdapter(articleAdapterBookmark);
        recyclerView.setLayoutManager(new LinearLayoutManager(BookmarkActivity.this));

        try {
            articleRoomViewModel.getAllArticleBookmark().observe(BookmarkActivity.this, countrydata -> {
                if (countrydata != null) {
                    articleAdapterBookmark.submitList(countrydata);
                }
            });
        }catch (Exception e){
            Toast.makeText(BookmarkActivity.this, "DATA SEE LATER EMPTY!", Toast.LENGTH_SHORT).show();
        }

    }

    public void searchitem(String country) {
        articleRoomViewModel.getsinglearticlebookmark(country.toUpperCase()).observe(BookmarkActivity.this, countryseelater -> {
            if (countryseelater != null) {
                articleAdapterBookmark.submitList(countryseelater);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        SearchManager sm = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView sv = (SearchView) menu.findItem(R.id.search).getActionView();
        sv.setSearchableInfo(sm.getSearchableInfo(BookmarkActivity.this.getComponentName()));
        sv.setIconifiedByDefault(true);
        sv.setMaxWidth(Integer.MAX_VALUE);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchitem(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(TextUtils.isEmpty(s)){
                    try {
                        articleRoomViewModel.getAllArticleBookmark().observe(BookmarkActivity.this, countrydata -> {
                            if (countrydata != null) {
                                articleAdapterBookmark.submitList(countrydata);
                            }
                        });
                    }catch (Exception e){
                        Toast.makeText(BookmarkActivity.this, "DATA BOOKMARK EMPTY!", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;
            }
        });
        return true;
    }

    public void onBackPressed(){
        //super.onBackPressed();
        Intent intent = new Intent(BookmarkActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        this.startActivity(intent);
        finish();
    }
}
