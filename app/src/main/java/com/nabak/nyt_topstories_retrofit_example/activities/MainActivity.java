package com.nabak.nyt_topstories_retrofit_example.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.Toast;

import com.nabak.nyt_topstories_retrofit_example.EndlessRecyclerViewScrollListener;
import com.nabak.nyt_topstories_retrofit_example.R;
import com.nabak.nyt_topstories_retrofit_example.SpacesItemDecoration;
import com.nabak.nyt_topstories_retrofit_example.adapters.ArticleAdapter;
import com.nabak.nyt_topstories_retrofit_example.model.NytArticleRepo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nabak on 2017. 1. 27..
 */

public class MainActivity extends Activity {

    public static final String NYT_TOPNEWS_URL = "https://api.nytimes.com/"; // input base url
    public static final String NYT_API = " "; // input your apikey
    RecyclerView recyclerView;
    NytArticleRepo nytArticleRepo;
    ArrayList<NytArticleRepo.results> articles;
    ArticleAdapter adapter;
    ProgressDialog progressDialog;

    String checkArticleTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {

        articles = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        adapter = new ArticleAdapter(articles);
        recyclerView.setAdapter(adapter);

        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        SpacesItemDecoration decoration = new SpacesItemDecoration(16);
        recyclerView.addItemDecoration(decoration);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait..");
        progressDialog.show();

        getArticleInfo();

        recyclerView.setOnScrollListener(new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {

            }
        });
    }

    private void getArticleInfo() {

        Retrofit client = new Retrofit.Builder()
                .baseUrl(NYT_TOPNEWS_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NytArticleRepo.NytApiInterface service = client.create(NytArticleRepo.NytApiInterface.class);
        Call<NytArticleRepo> call = service.getNytRetrofit(NYT_API);

        call.enqueue(new Callback<NytArticleRepo>() {
            @Override
            public void onResponse(Call<NytArticleRepo> call, retrofit2.Response<NytArticleRepo> response) {
                if(response.isSuccessful()){
                    nytArticleRepo = response.body();
                    progressDialog.dismiss();
                    checkArticleTime = nytArticleRepo.getLast_updated();
                    Log.d("nabak", nytArticleRepo.getLast_updated());
                    adapter.enroll(nytArticleRepo.getResults());
                }
            }

            @Override
            public void onFailure(Call<NytArticleRepo> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_LONG).show();
            }
        });
    }

}
