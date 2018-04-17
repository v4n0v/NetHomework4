package com.example.avetc.nethomework4.image;

import java.io.File;

public interface ICacheImage {

    void writeToCache(File file, String url);
    File readFromCache(String url);

}
