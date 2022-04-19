package com.harshit.goswami.pixelwallpaper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.harshit.goswami.pixelwallpaper.Modals.photos;
import com.harshit.goswami.pixelwallpaper.Modals.src;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{
    ArrayList<src> WallList;
    wallpaperAdapter adapter;
    ProgressDialog dialog;
    RecyclerView recyclerView;
    SearchView search;
    FloatingActionButton next,prev;
    int page=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        dialog =new ProgressDialog(this);
        dialog.setTitle("Loading...");
        recyclerView =findViewById(R.id.recyclerView);
        search =findViewById(R.id.search);
        next =findViewById(R.id.btn_next);
        prev =findViewById(R.id.btn_prev);


        CuratedPhotos();

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Search(query);
                return true;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Search(newText);
                return true;
            }
        });

        next.setOnClickListener(View-> {
            ++page;
            CuratedPhotos();
            //Search("new");
        });
        prev.setOnClickListener(View->{
            if(page==1){
                Toast.makeText(getApplicationContext(), "No prev Wallpaper", Toast.LENGTH_SHORT).show();
            }
            else{
                --page;
                //Search("hd");
                CuratedPhotos();
            }
        });

    }

    public void CuratedPhotos(){
        RetrofitApi retrofitApi =new RetrofitApi(this);
        RetrofitApi.getApiInterface().getWallpaper(page,40).enqueue(new Callback<photos>() {
            @Override
            public void onResponse(Call<photos> call, Response<photos> response) {
                if(response.isSuccessful())
                {
                    recyclerView = findViewById(R.id.recyclerView);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayout.VERTICAL));
                    adapter = new wallpaperAdapter(getApplicationContext(),response.body().getPhotos());
                    recyclerView.setAdapter(adapter);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Error...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<photos> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failure..."+t, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public  void Search(String quary){

        RetrofitApi.getApiInterface().getSearchWallpaper(quary,page,80).enqueue(new Callback<photos>() {
            @Override
            public void onResponse(Call<photos> call, Response<photos> response) {
                if(response.isSuccessful())
                {
                    recyclerView = findViewById(R.id.recyclerView);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,LinearLayout.VERTICAL));
                    adapter =new wallpaperAdapter(getApplicationContext(),response.body().getPhotos());
                    recyclerView.setAdapter(adapter);

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "not able to get", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<photos> call, Throwable t) {
                Toast.makeText(getApplicationContext(), (CharSequence) t, Toast.LENGTH_SHORT).show();
            }
        });
    }
}