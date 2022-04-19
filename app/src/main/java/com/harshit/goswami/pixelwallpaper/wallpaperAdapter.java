package com.harshit.goswami.pixelwallpaper;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.harshit.goswami.pixelwallpaper.Modals.src;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class wallpaperAdapter extends RecyclerView.Adapter<wallpaperAdapter.ViewHolder> {
    Context context;
    ArrayList<src> wallpaperList;

    public wallpaperAdapter(Context context, ArrayList<src> wallpaperList) {
        this.context = context;
        this.wallpaperList = wallpaperList;
    }

    // ArrayList<int>

    @NonNull
    @Override
    public wallpaperAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(context).inflate(R.layout.wallpaper_item, parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull wallpaperAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(wallpaperList.get(position).getSrc().getPortrait()).into(holder.imageView);
        //Picasso.get().load(wallpaperList.get(position).getSrc().getPortrait()).into(holder.imageView);
        holder.imageView.setOnClickListener(View ->{
            Intent intent =new Intent(context,set_wallpaper.class);
            intent.putExtra("wallpaper",wallpaperList.get(position).getSrc().getPortrait());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        });

    }

    @Override
    public int getItemCount() {
        return wallpaperList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView =itemView.findViewById(R.id.image);
        }
    }
}
