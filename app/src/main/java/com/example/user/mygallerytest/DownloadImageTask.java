package com.example.user.mygallerytest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by User on 03.02.2015.
 */
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

    private ImageView bmImage;
    private String message = null;
    private Context context;
    private String newString;
    private MyCallBack myCallBack;
    public DownloadImageTask(Context context, ImageView bmImage, MyCallBack myCallBack) {
        this.bmImage = bmImage;
        this.context = context;
        this.myCallBack = myCallBack;
    }

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
    }

    protected Bitmap doInBackground(String... urls) {
        Bitmap bitmap = null;
        String urldisplay = urls[0];
        InputStream iStream = null;

        try {
            URL url = new URL(urldisplay);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            iStream = urlConnection.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(iStream));
            message = in.readLine();
            newString = message.substring(message.lastIndexOf("/") + 1);

            URL wallpaperURL = new URL(message);
            URLConnection connection = wallpaperURL.openConnection();
            connection.connect();
            InputStream inputStream = new BufferedInputStream(wallpaperURL.openStream(), 10240);
            File cacheDir = getCacheFolder(context);
            File cacheFile = new File(cacheDir, newString);

            FileOutputStream outputStream = new FileOutputStream(cacheFile);

            byte buffer[] = new byte[1024];
            int dataSize;
            while ((dataSize = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, dataSize);
            }

            outputStream.flush();
            outputStream.close();


            //bitmap = downloadUrl(message);

            File cacheDirToLoad = getCacheFolder(context);
            File cacheFileToLoad = new File(cacheDirToLoad, newString);

            InputStream fileInputStream = new FileInputStream(cacheFileToLoad);
            BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
            bitmapOptions.inSampleSize = 3;
            bitmapOptions.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeStream(fileInputStream, null, bitmapOptions);
        } catch (Exception e) {
            Log.d("Exception", e.toString());

        } finally {
            try {
                iStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }


    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        bmImage.setImageBitmap(result);
        myCallBack.callBack(newString);
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

    public File getDataFolder(Context context) {
        File dataDir = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            dataDir = new File(Environment.getExternalStorageDirectory(), "myappdata");
            if(!dataDir.isDirectory()) {
                dataDir.mkdirs();
            }
        }

        if(!dataDir.isDirectory()) {
            dataDir = context.getFilesDir();
        }

        return dataDir;
    }

}