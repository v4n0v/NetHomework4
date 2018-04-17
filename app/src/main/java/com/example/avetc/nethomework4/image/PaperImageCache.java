package com.example.avetc.nethomework4.image;

import java.io.File;

import io.paperdb.Paper;
import timber.log.Timber;


public class PaperImageCache implements ICacheImage {

    @Override
    public void writeToCache(File file ,String url) {

        Paper.book("images").delete(url);
        Paper.book("images").write(url, file);
        Timber.d("paper saved image: "+file);
    }

    @Override
    public File readFromCache(String url) {
        File file = Paper.book("images").read(url);
        Timber.d("paper loaded image: "+file);
         return file;
    }

}
