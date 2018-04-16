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
import com.example.avetc.nethomework4.entities.realm.RealmImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import io.paperdb.Paper;
import io.realm.Realm;
import timber.log.Timber;



public class GlideLoader implements ImageLoader<ImageView> {
    private final String FILENAME = "avatar.jpg";
//
//    @Override
//    public void loadInto(@Nullable String url, ImageView container)
//    {
//        if(Paper.book("images").contains(MD5(url)))
//        {
//            byte[] bytes = Paper.book("images").read(MD5(url));
//            Glide.with(container.getContext())
//                    .load(bytes)
//                    .into(container);
//        }
//        else
//        {
//            GlideApp.with(container.getContext()).asBitmap().load(url).listener(new RequestListener<Bitmap>()
//            {
//                @Override
//                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource)
//                {
//                    Timber.e("failed to load image", e);
//                    return false;
//                }
//
//                @Override
//                public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource)
//                {
//                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                    resource.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//                    Paper.book("images").write(MD5(url), stream.toByteArray());
//                    return false;
//                }
//            }).into(container);
//        }
//    }


    @Override
    public void loadInto(@Nullable String url, ImageView container) {
        Realm realm = Realm.getDefaultInstance();
        RealmImage realmImage = realm.where(RealmImage.class).equalTo("url", url).findFirst();

        // если в базе нет записи об аватаре, тогда загружаем, картинку, сохраняем в файл
        if (realmImage == null) {
            GlideApp.with(container.getContext()).asBitmap().load(url).listener(new RequestListener<Bitmap>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                    Timber.e("failed to load image", e);
                    return false;
                }

                @Override
                public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                    //
                    Timber.d("saving avatar into phone memory");
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    resource.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    File file = new File(container.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), FILENAME);
                    try {
                        stream.writeTo(new FileOutputStream(file));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    realm.executeTransaction(realm1 -> {
                        RealmImage newRealmImage = realm.createObject(RealmImage.class, url);
                        newRealmImage.setPath(file.getAbsolutePath());
                    });
                    Timber.d("avatar saved in phone memory, path added to realm database");
                    return false;
                }
            }).into(container);
        } else {
            File file = new File(realmImage.getPath());
            Timber.d("loading avatar from phone memory\n" + file.getAbsolutePath());
            Glide.with(container.getContext())
                    .load(file)
                    .into(container);
            Timber.d("avatar loaded from phone memory ");


        }
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
