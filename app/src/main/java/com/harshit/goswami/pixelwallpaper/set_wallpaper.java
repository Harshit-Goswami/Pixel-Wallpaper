package com.harshit.goswami.pixelwallpaper;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;

import retrofit2.http.Url;

public class set_wallpaper extends AppCompatActivity {
    ImageView wallpaper;
    Button btn_setWallpaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_wallpaper);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        wallpaper =findViewById(R.id.setWallpaper);
        btn_setWallpaper =findViewById(R.id.btn_SetWallpaper);
        ImageButton download =(ImageButton)findViewById(R.id.btn_dawnload);

        Intent intent = getIntent();
        String Wallpaper =intent.getStringExtra("wallpaper");
        Glide.with(this).load(Wallpaper).into(wallpaper);

        btn_setWallpaper.setOnClickListener(View->{
            Bitmap bitmap =((BitmapDrawable) wallpaper.getDrawable()).getBitmap();
            WallpaperManager wallpaperManager =WallpaperManager.getInstance(this);
            try {
                wallpaperManager.setBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Toast.makeText(getApplicationContext(), "Set Successfully", Toast.LENGTH_SHORT).show();
        });

        download.setOnClickListener(View ->{
            DownloadManager downloadManager =null;
            downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            Uri uri =Uri.parse(Wallpaper);
            DownloadManager.Request request =new DownloadManager.Request(uri);

            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                    .setAllowedOverRoaming(false)
                    .setTitle(Wallpaper)
                    .setMimeType("image/jpeg")
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES,Wallpaper+".jpg");
        downloadManager.enqueue(request);
            Toast.makeText(getApplicationContext(), "Download Completed!", Toast.LENGTH_SHORT).show();
        });

    }
}






