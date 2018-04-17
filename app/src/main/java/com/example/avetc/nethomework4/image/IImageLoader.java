package com.example.avetc.nethomework4.image;

public interface IImageLoader<T> {
    void loadInto(String url, T container);
}
