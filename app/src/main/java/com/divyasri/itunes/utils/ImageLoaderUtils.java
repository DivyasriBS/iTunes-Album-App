package com.divyasri.itunes.utils;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


public class ImageLoaderUtils {

    public static void display(@NonNull ImageView imageView, @NonNull String url, int placeholder, int error) {
        Glide.with(imageView.getContext())
                .load(url)
                .apply(RequestOptions.placeholderOf(placeholder).error(error))
                .into(imageView);

    }

    public static void displayLogo(@NonNull ImageView imageView, @NonNull Bitmap url, int placeholder, int error) {
        Glide.with(imageView.getContext())
                .load(url)
                .apply(RequestOptions.placeholderOf(placeholder).error(error))
                .into(imageView);

    }
    /*public static void display(@NonNull ImageView imageView, @NonNull Object url) {
        Glide.with(imageView.getContext()).load(url).placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher).crossFade().into(imageView);
    }*/

//    public static Bitmap showImagefromBase64(String ImageStr)
//    {
//        Bitmap decodedByte = null;
//        if(!Utility.isNullOrEmpty(ImageStr)) {
//            String pureBase64Encoded = ImageStr.substring(ImageStr.indexOf(",") + 1);
//            byte[] decodedString = Base64.decode(pureBase64Encoded, Base64.DEFAULT);
//
//            decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//            return decodedByte;
//        }
//        return decodedByte;
//    }
}

