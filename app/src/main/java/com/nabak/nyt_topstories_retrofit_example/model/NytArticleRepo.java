package com.nabak.nyt_topstories_retrofit_example.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by nabakgood on 2017-02-12.
 */

public class NytArticleRepo {

    @SerializedName("last_updated") String last_updated;
    @SerializedName("num_results") Integer num_results;

    public String getLast_updated() {
        return last_updated;
    }

    public Integer getNum_results() {
        return num_results;
    }

    public ArrayList<results> results = new ArrayList<>();
    public ArrayList<results> getResults() { return  results;}

    public class results{
        @SerializedName("title") String title;
        @SerializedName("url") String url;

        public ArrayList<multimedia> multimedia = new ArrayList<>();
        public ArrayList<multimedia> getMultimedia(){ return multimedia;}

        public String getTitle() {
            return title;
        }

        public String getUrl() {
            return url;
        }

        public class multimedia {
            @SerializedName("url") String url;

            public String getUrl() {
                return  url;
            }
        }
    }

    public interface NytApiInterface{
        @Headers({"Accept: application/json"})
        @GET("svc/topstories/v2/home.json")
        Call<NytArticleRepo> getNytRetrofit(@Query("api-key") String api_key);
    }
}
