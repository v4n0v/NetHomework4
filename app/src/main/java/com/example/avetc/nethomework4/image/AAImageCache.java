package com.example.avetc.nethomework4.image;

import com.activeandroid.query.Select;
import com.example.avetc.nethomework4.entities.active_android.AAImage;

import java.io.File;

import timber.log.Timber;


public class AAImageCache implements ICacheImage {
    @Override
    public void writeToCache(File file, String url) {
        Timber.d("loadind db aaImage ");
        AAImage aaImage = new Select()
                .from(AAImage.class)
                .where("url = ?", url)
                .executeSingle();

        Timber.d("aa db aaImage "+ aaImage.file);
        if (aaImage == null){
            aaImage = new AAImage();
            aaImage.url = url;
        }

        aaImage.file = file;
        aaImage.save();
        Timber.d("active_android saved image: "+ aaImage.file);

    }

    @Override
    public File readFromCache(String url) {
        AAImage aaImage = new Select()
                .from(AAImage.class)
                .where("url = ?", url)
                .executeSingle();
        if (aaImage != null) {
            Timber.d("active_android loaded image: "+ aaImage.file);
           return aaImage.file;
        }
        Timber.d("no image in active_android database");
        return null;
    }
}
