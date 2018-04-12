package com.example.avetc.nethomework4.image;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by avetc on 11.04.2018.
 */

public class GlideLoader implements ImageLoader<ImageView>{
    @Override
    public void loadInto(String url, ImageView container) {
        Glide.with(container.getContext()).load(url).into(container);
    }
}
