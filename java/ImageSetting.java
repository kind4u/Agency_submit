package com.example.admin.agency;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by a on 2018-06-08.
 */

public class ImageSetting extends AsyncTask<String, Void, Bitmap> {
    Bitmap bitmap = null;
    ImageView img;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public static String[] thumbArr;

    public static String[] getThumbArr() {
        return thumbArr;
    }

    public static void setThumbArr(String[] thumbArr) {
        ImageSetting.thumbArr = thumbArr;
    }

    protected void imageView(ImageView image) {
        img = image;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        Log.d("인자확인", params[0]);

        String thumbUrl = params[0].toString();

        try {
            URL url = new URL(thumbUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.connect();
            bitmap = BitmapFactory.decodeStream(conn.getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }


    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);

        img.setImageBitmap(bitmap);
    }


    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

}
