package com.example.avetc.nethomework4.image;

import android.graphics.Bitmap;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.avetc.nethomework4.common.NetworkStatus;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.inject.Inject;

import timber.log.Timber;



public class GlideLoader implements IImageLoader<ImageView> {

    @Inject
    ICacheImage cacheImage;

    public GlideLoader(ICacheImage cacheImage) {
        this.cacheImage = cacheImage;
    }

    @Override
    public void loadInto(@Nullable String url, ImageView container) {

        if (NetworkStatus.isOnline()) {
            GlideApp.with(container.getContext()).asBitmap().load(url).listener(new RequestListener<Bitmap>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                    Timber.e("failed to load image", e);
                    return false;
                }

                @Override
                public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                    //
                    Timber.d("saving avatar into phone memory "+url);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    resource.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    File file = new File(container.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), MD5(url)+".jpg");
                    try {
                        stream.writeTo(new FileOutputStream(file));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    cacheImage.writeToCache(file, MD5(url));
                    Timber.d("avatar saved in phone memory, path added to realm database");
                    return false;
                }
            }).into(container);
        } else {
            File file = cacheImage.readFromCache(MD5(url));
            if(file!=null) {
                Timber.d("loading avatar from phone memory\n" + file.getAbsolutePath());
                Glide.with(container.getContext())
                        .load(file)
                        .into(container);
                Timber.d("avatar loaded from phone memory ");
            }

        }
    }

    private String getImageName(String url){
        String[] s = url.split("/");
        return s[s.length];
    }
    public static String MD5(String s) {
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        m.update(s.getBytes(), 0, s.length());
        return new BigInteger(1, m.digest()).toString(16);
    }


}
