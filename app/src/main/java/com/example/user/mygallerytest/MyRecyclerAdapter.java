package com.example.user.mygallerytest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.user.mygallerytest.global.Constants;

import java.util.List;

/**
 * Created by User on 03.02.2015.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<Picture> items;
    private int itemLayout;
    private MyCallBack myCallBack;


    public MyRecyclerAdapter(Context context, List<Picture> items, int itemLayout, MyCallBack myCallBack) {
        this.items = items;
        this.itemLayout = itemLayout;
        this.context = context;
        this.myCallBack = myCallBack;
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new ViewHolder(v);
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
       // Picture item = items.get(position);

       // holder.image.setImageBitmap(null);
        loadImage(holder.image);
       // holder.itemView.setTag(item);
    }

    @Override public int getItemCount() {
        return items.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
        }
    }

    private void loadImage(ImageView _imageView) {
        new DownloadImageTask(context, _imageView, myCallBack).execute(Constants.URL);

    }
}