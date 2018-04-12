package com.example.avetc.nethomework4.image;

/**
 * Created by avetc on 11.04.2018.
 */

public interface ImageLoader<T> {
    void loadInto(String url, T container);
}
