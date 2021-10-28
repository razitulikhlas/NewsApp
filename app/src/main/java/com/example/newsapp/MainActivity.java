package com.example.newsapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.newsapp.room.ArticleRoomViewModel;
import com.example.newsapp.room.ArticleTable;
import com.example.newsapp.room.ArticleViewModel;
import com.example.newsapp.sessionmanager.SessionManagerUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements CategoryRVAdapter.CategoryClickInterface {

    private SessionManagerUtil sessionManager = new SessionManagerUtil();
    private String fullname;
    private String email;
    private Intent tempIntent;
    boolean isAllowed;

    private RecyclerView newsRV, categoryRV;
    private ProgressBar loadingPB;
    private ArrayList<Articles> articlesArrayList;
    private ArrayList<CategoryRVModal> categoryRVModalArrayList;
    private CategoryRVAdapter categoryRVAdapter;
    private NewsRVAdapter newsRVAdapter;
    private ArticleAdapter articleAdapter;
    private Button buttonbookmark, logoutbutton;
    private ArticleViewModel articleViewModel;
    private ArticleRoomViewModel articleRoomViewModel;


    private Executor backgroundThread = Executors.newSingleThreadExecutor();
    private Executor mainThread = new Executor() {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());
        @Override
        public void execute(Runnable command) {
            mainThreadHandler.post(command);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isAllowed = SessionManagerUtil.getInstance().isSessionActive(this, Calendar.getInstance().getTime());
        setContentView(R.layout.activity_main);
        tempIntent = getIntent();
        fullname = tempIntent.getStringExtra("name_key");
        email = tempIntent.getStringExtra("email_key");

        newsRV = findViewById(R.id.idRVNews);
        categoryRV = findViewById(R.id.idRVCategories);
        loadingPB = findViewById(R.id.idPBLoading);
        buttonbookmark = (Button)findViewById(R.id.bookmark);


//        if(isAllowed) {
//            Toast.makeText(MainActivity.this, "Login Success " + sessionManager.getUser(MainActivity.this).toUpperCase() + "", Toast.LENGTH_LONG).show();
//        }

        articlesArrayList = new ArrayList<>();
        categoryRVModalArrayList = new ArrayList<>();
        newsRVAdapter = new NewsRVAdapter(articlesArrayList, this);
        categoryRVAdapter = new CategoryRVAdapter(categoryRVModalArrayList,this,this::onCategoryClick);
        newsRV.setLayoutManager(new LinearLayoutManager(this));
        newsRV.setAdapter(newsRVAdapter);
        categoryRV.setAdapter(categoryRVAdapter);
        getCategories();
        getNews("All");
        newsRVAdapter.notifyDataSetChanged();

    }

    private void getCategories() {
        categoryRVModalArrayList.add(new CategoryRVModal("All", "https://media.istockphoto.com/photos/online-news-on-a-smartphone-and-laptop-woman-reading-news-or-articles-picture-id1219980553?b=1&k=20&m=1219980553&s=170667a&w=0&h=HGLxwS06WjWFl9TSEKwmRbPtz9AS4og49opQ_2uxYCk="));
        categoryRVModalArrayList.add(new CategoryRVModal("Technology", "https://media.istockphoto.com/photos/data-scientists-male-programmer-using-laptop-analyzing-and-developing-picture-id1295900106?b=1&k=20&m=1295900106&s=170667a&w=0&h=kQ2UWilU4Bild5aP03CaF65gMbSI-chG--7dd2oS8GM="));
        categoryRVModalArrayList.add(new CategoryRVModal("Science", "https://media.istockphoto.com/photos/asian-female-doctor-working-with-pathogen-samples-using-microscope-picture-id1256323051?b=1&k=20&m=1256323051&s=170667a&w=0&h=mrZs1Fzc0yjnus3l0WALeqTgE7SWhdPpBZ4C6gTQ2Wg="));
        categoryRVModalArrayList.add(new CategoryRVModal("Sports", "https://media.istockphoto.com/photos/various-sport-equipments-on-grass-picture-id949190736?b=1&k=20&m=949190736&s=170667a&w=0&h=f3ofVqhbmg2XSVOa3dqmvGtHc4VLA_rtbboRGaC8eNo="));
        categoryRVModalArrayList.add(new CategoryRVModal("General", "https://media.istockphoto.com/photos/skyline-at-dusk-picture-id1275297551?b=1&k=20&m=1275297551&s=170667a&w=0&h=UEbfE8f3evN8kHuqYNU0itgXKzHsfPTA5vqtzSI60i0="));
        categoryRVModalArrayList.add(new CategoryRVModal("Business", "https://media.istockphoto.com/photos/large-group-of-business-people-in-convention-centre-picture-id1281724535?b=1&k=20&m=1281724535&s=170667a&w=0&h=RV0k68y2VPMDnP6QlW_7kErXhbLcqjYVgNmwc3kMMLo="));
        categoryRVModalArrayList.add(new CategoryRVModal("Entertainment", "https://images.unsplash.com/photo-1525869811964-53594bfcb4b0?ixid=MnwxMjA3fDB8MHxzZWFyY2h8Nnx8ZW50ZXJ0YWlubWVudHxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=600&q=60"));
        categoryRVModalArrayList.add(new CategoryRVModal("Health", "https://images.unsplash.com/photo-1498837167922-ddd27525d352?ixid=MnwxMjA3fDB8MHxzZWFyY2h8OXx8aGVhbHRofGVufDB8fDB8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=600&q=60"));
        categoryRVAdapter.notifyDataSetChanged();
    }

    private void getNews(String category) {
        //loadingPB.setVisibility(View.VISIBLE);
        articlesArrayList.clear();
        String categoryURL = "https://newsapi.org/v2/top-headlines?country=id&category=" + category + "&apikey=e24dc0cf8f7a402ea31d1356147237aa";
        String url = "https://newsapi.org/v2/top-headlines?country=id&sortBy=publishedAt&apikey=e24dc0cf8f7a402ea31d1356147237aa";
        String BASE_URL = "https://newsapi.org/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<NewsModal> call;
        if (category.equals("All")) {
            call = retrofitAPI.getAllNews(url);
        } else {
            call = retrofitAPI.getNewsByCategory(categoryURL);
        }

        call.enqueue(new Callback<NewsModal>() {
            @Override
            public void onResponse(Call<NewsModal> call, Response<NewsModal> response) {
                NewsModal newsModal = response.body();
                //loadingPB.setVisibility(View.GONE);
                ArrayList<Articles> articles = newsModal.getArticles();
                for (int i=0; i<articles.size(); i++) {
                    articlesArrayList.add(new Articles(articles.get(i).getTitle(),articles.get(i).getAuthor(), articles.get(i).getDescription(), articles.get(i).getUrlToImage(),
                            articles.get(i).getUrl(), articles.get(i).getContent(), articles.get(i).getPublishedAt()));
                }
                newsRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<NewsModal> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Fail to get news", Toast.LENGTH_SHORT).show();;
            }
        });
    }

    public void insertdata(){
        articleViewModel.ArticleData().observe(MainActivity.this, new Observer<List<Articles>>() {
            @Override
            public void onChanged(List<Articles> news) {
                final int size = news.size();
                for (int i = 0; i < size; i++) {
                    ArticleTable articleTable = new ArticleTable();
                    articleTable.title=news.get(i).getTitle();
                    articleTable.description=news.get(i).getDescription();
                    articleTable.urlToImage = news.get(i).getUrlToImage();
                    articleTable.url = news.get(i).getUrl();
                    articleTable.content = news.get(i).getContent();
                    articleTable.publishedAt=news.get(i).getPublishedAt();
                    articleRoomViewModel.insert(articleTable);
                }
            }
        });
    }

    public void viewdata(){
        articleRoomViewModel.getAllArticle().observe(MainActivity.this, articledata -> {
            if (articledata != null) {
//                loadingPB.setVisibility(View.GONE);
                buttonbookmark.setVisibility(View.VISIBLE);
                logoutbutton.setVisibility(View.VISIBLE);
                articleAdapter.submitList(articledata);
            }
        });
    }

    @Override
    public void onCategoryClick(int position) {
        String category = categoryRVModalArrayList.get(position).getCategory();
        getNews(category);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });
        return true;

    }

    public void filter(String text) {
        ArrayList<Articles> filteredlist = new ArrayList<>();
        for (Articles item : articlesArrayList) {
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            Toast.makeText(this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
        } else {
            newsRVAdapter.filterList(filteredlist);
        }
    }

    public void bookmark(View view){
        Intent intent = new Intent(MainActivity.this, BookmarkActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        this.startActivity(intent);
        finish();
    }
    public void logout(View view){
        SessionManagerUtil.getInstance().endUserSession(MainActivity.this);
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(intent);
    }

    public void profile(View view){
        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("fullname",fullname);
        intent.putExtra("email",email);
        this.startActivity(intent);
    }
}