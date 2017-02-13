package com.nabak.nyt_topstories_retrofit_example.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.nabak.nyt_topstories_retrofit_example.R;
import com.nabak.nyt_topstories_retrofit_example.model.NytArticleRepo;

/**
 * Created by nabak on 2017. 1. 27..
 */

public class ArticleActivity extends Activity {
//
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        String articleUrl =  getIntent().getStringExtra("article");
        WebView webView = (WebView) findViewById(R.id.webView);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressDialog.dismiss();
            }
        });
        webView.loadUrl(articleUrl);
    }
}
