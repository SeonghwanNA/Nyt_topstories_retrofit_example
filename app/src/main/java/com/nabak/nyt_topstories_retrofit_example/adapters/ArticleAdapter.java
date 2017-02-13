package com.nabak.nyt_topstories_retrofit_example.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.nabak.nyt_topstories_retrofit_example.R;
import com.nabak.nyt_topstories_retrofit_example.activities.ArticleActivity;
import com.nabak.nyt_topstories_retrofit_example.model.NytArticleRepo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by nabak on 2017. 1. 27..
 */

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout cardLayout;
        ImageView thumbnail;
        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            cardLayout = (RelativeLayout) itemView.findViewById(R.id.cardLayout);
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }

    private ArrayList<NytArticleRepo.results> mArticles;
    private Context context;

    public ArticleAdapter(ArrayList<NytArticleRepo.results> articles){
        mArticles = articles;
    }

    @Override
    public ArticleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View articleView = inflater.inflate(R.layout.cardview_layout, parent, false);

        ViewHolder viewHolder = new ViewHolder(articleView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ArticleAdapter.ViewHolder holder, final int position) {

        final NytArticleRepo.results article = mArticles.get(position);
        String thumbnail = "";
/*        Log.d("nabak", "이미지 개수 :" + size);
        Log.d("nabak", thumbnail);
        Log.d("nabak", article.getTitle());*/

        holder.thumbnail.setImageResource(0);
        holder.title.setText(article.getTitle());

       if( article.getMultimedia().size() > 0) {
           thumbnail = article.getMultimedia().get(2).getUrl();
        } else {
           thumbnail = "";
       }

        if (!TextUtils.isEmpty(thumbnail)) {
            holder.thumbnail.setVisibility(View.VISIBLE);
            //Glide.with(context).load(thumbnail).into(holder.thumbnail);
           Picasso.with(context).load(thumbnail).into(holder.thumbnail);
        } else {
            holder.thumbnail.setVisibility(View.GONE);
        }

        holder.cardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ArticleActivity.class);
                i.putExtra("article", article.getUrl());
                context.startActivity(i);
            }
        });
    }

    public void enroll(ArrayList<NytArticleRepo.results> articles){
        mArticles.clear();
        mArticles.addAll(articles);
        //Log.d("shna2", articles.get(0).getMultimedia().get(2).getThumbnail());
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }



}
