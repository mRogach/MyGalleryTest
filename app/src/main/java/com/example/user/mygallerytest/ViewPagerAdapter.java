package com.example.user.mygallerytest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

    Context context;
    List <Picture> list;
    LayoutInflater inflater;
    private Bitmap bitmap;
    public ViewPagerAdapter (Context context, List<Picture> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        ImageView image;
        Bitmap bitmap1;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.detail_image_fragment, container,
                false);

        image = (ImageView) itemView.findViewById(R.id.img);
        bitmap1 = getImage(list.get(position).getmUrl());
        image.setImageBitmap(bitmap1);
        (container).addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);

    }
    public Bitmap getImage(String imageUrl){
        File cacheDirToLoad = getCacheFolder(context);
        File cacheFileToLoad = new File(cacheDirToLoad, imageUrl);
        InputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(cacheFileToLoad);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = 0;
        bitmapOptions.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeStream(fileInputStream, null, bitmapOptions);
        return bitmap;
    }

    public File getCacheFolder(Context context) {
        File cacheDir = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheDir = new File(Environment.getExternalStorageDirectory(), "cachefolder");
            if (!cacheDir.isDirectory()) {
                cacheDir.mkdirs();
            }
        }

        if (!cacheDir.isDirectory()) {
            cacheDir = context.getCacheDir(); //get system cache folder
        }

        return cacheDir;
    }
}
