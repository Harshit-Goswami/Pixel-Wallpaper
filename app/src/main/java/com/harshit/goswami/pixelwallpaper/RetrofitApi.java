package com.harshit.goswami.pixelwallpaper;

import android.content.Context;


import com.harshit.goswami.pixelwallpaper.Modals.photos;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public class RetrofitApi {
    Context context;

    public RetrofitApi(Context context) {
        this.context = context;
    }

    Retrofit retrofit =new Retrofit.Builder()
            .baseUrl("https://api.pexels.com/v1/")
           .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static ApiInterface getApiInterface(){
        Retrofit retrofit =null;
        if(retrofit==null){
            retrofit =new Retrofit.Builder()
                    .baseUrl(ApiInterface.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApiInterface.class);
    }


    public interface ApiInterface{

    public String BASE_URL ="https://api.pexels.com/v1/";//\n

        @Headers({"Accept: application/json",
                "Authorization: 563492ad6f917000010000011a3074bc3e2c4594a4dabb60696cee77"})
        @GET("curated/")
        Call<photos> getWallpaper(
                @Query("page") int page,//@Query("page") int page,
                @Query("per_page") int per_page);

        @Headers("Authorization: 563492ad6f917000010000011a3074bc3e2c4594a4dabb60696cee77")
        @GET("search")
        Call<photos> getSearchWallpaper(
                @Query("query") String query,
                @Query("page") int page,
                @Query("per_page") int per_page );



    }
}
